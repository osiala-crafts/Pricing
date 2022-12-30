package com.pricing.domain.services;

import com.pricing.application.dto.*;
import com.pricing.application.exeptions.InvalidStationException;
import com.pricing.application.exeptions.NoPricingRuleException;
import com.pricing.domain.entities.PricingConfiguration;
import com.pricing.domain.entities.PricingRule;
import com.pricing.domain.entities.Station;
import com.pricing.domain.entities.StationType;
import com.pricing.domain.ports.IPricingService;
import com.pricing.domain.ports.PricingConfigurationRepository;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PricingService implements IPricingService {

    private PricingConfigurationRepository pricingConfigurationRepository;

    public PricingService(PricingConfigurationRepository pricingConfigurationRepository) {
        this.pricingConfigurationRepository = pricingConfigurationRepository;
    }

    /**
     * Price All customers journeys
     *
     * @param candidateInputDto candidateInputDto tap entries
     * @return CandidateOutputDto the customer summaries result
     * @throws IOException this exception will be thrown when configuration file can't be opened
     */
    @Override
    public CandidateOutputDto price(final CandidateInputDto candidateInputDto) throws IOException {
        if (candidateInputDto == null) {
            return new CandidateOutputDto();
        }
        final PricingConfiguration pricingConfiguration = this.pricingConfigurationRepository.loadPricingConfiguration();

        Map<Long, List<List<TapDto>>> tapsByCustomerAndByJourney = groupTapsByCustomerAndByJourney(candidateInputDto);

        List<CustomerSummaryDto> customerSummaries = tapsByCustomerAndByJourney.entrySet().stream().map(entry -> {
            final Function<List<TapDto>, TripDto> tripCalculator = getTripCalculatorFunction(pricingConfiguration);
            final List<TripDto> trips = entry.getValue().stream().map(tripCalculator).collect(Collectors.toList());
            final Integer totalCostInCents = trips.stream().mapToInt(TripDto::getCostInCents).sum();
            return new CustomerSummaryDto(entry.getKey(), totalCostInCents, trips);
        }).collect(Collectors.toList());
        return new CandidateOutputDto(customerSummaries);
    }

    /**
     * This method will group taps by customer (Long: customerId) and then group them by journey (by pairs, entry and exit)
     *
     * @param candidateInputDto candidateInputDto entries
     * @return Map<Long, List < List < TapDto>>> Grouped taps by customer and by Journey (pair of taps, entry and exit)
     */
    private Map<Long, List<List<TapDto>>> groupTapsByCustomerAndByJourney(final CandidateInputDto candidateInputDto) {

        final Map<Long, List<TapDto>> tapsByCustomer = candidateInputDto.getTaps().stream().collect(Collectors.groupingBy(TapDto::getCustomerId));
        Map<Long, List<List<TapDto>>> tapsByCustomerAndByJourney = new HashMap<>();
        tapsByCustomer.forEach((key, value) -> {
            List<List<TapDto>> customerTapsPartitionedOnPairs = splitCustomerTapsOnPairs(value);
            tapsByCustomerAndByJourney.put(key, customerTapsPartitionedOnPairs);
        });
        return tapsByCustomerAndByJourney;
    }

    /**
     * This method split taps on pairs
     *
     * @param taps list of taps
     * @return list of pairs of taps
     */
    private List<List<TapDto>> splitCustomerTapsOnPairs(List<TapDto> taps) {

        int partitionSize = 2;
        final List<List<TapDto>> partitions = new ArrayList<>();

        for (int i = 0; i < taps.size(); i += partitionSize) {
            partitions.add(taps.subList(i, Math.min(i + partitionSize,
                    taps.size())));
        }
        return partitions;
    }

    /**
     * This method define a function transforming and calculating trip from Two taps (entry and exit)
     *
     * @param pricingConfiguration Pricing configuration containing stations, zones and pricing rules details
     * @return Function<List < TapDto>, TripDto> function calculating Trip from taps list (size = 2)
     */
    private Function<List<TapDto>, TripDto> getTripCalculatorFunction(final PricingConfiguration pricingConfiguration) {
        return (List<TapDto> taps) -> {
            final TripDto tripDto = new TripDto(taps.get(0).getStation(), taps.get(1).getStation(), taps.get(0).getUnixTimestamp());
            try {
                setFromAndToZones(pricingConfiguration, tripDto);
                tripDto.setCostInCents(calculateTripCost(pricingConfiguration, tripDto));
            } catch (InvalidStationException | NoPricingRuleException e) {
                e.printStackTrace();
            }
            return tripDto;
        };
    }

    /**
     * Using Pricing configuration and Trip details, this method calculate the Trip cost
     *
     * @param pricingConfiguration Pricing configuration containing stations, zones and pricing rules details
     * @param tripDto              Trip details
     * @return Trip Cost in cents
     * @throws NoPricingRuleException this exception will be thrown when no pricing rule correspond to the trip
     */
    private Integer calculateTripCost(final PricingConfiguration pricingConfiguration, final TripDto tripDto) throws NoPricingRuleException {
        Optional<PricingRule> pricingRuleOptional = pricingConfiguration.getPricingRules().stream()
                .filter(pricingRule -> pricingRule.getDepartureZone().getZoneId().equals(Integer.valueOf(tripDto.getZoneFrom()))
                        && pricingRule.getArrivalZone().getZoneId().equals(Integer.valueOf(tripDto.getZoneTo())))
                .findAny();
        if (pricingRuleOptional.isPresent()) {
            return pricingRuleOptional.get().getPrice();
        } else {
            throw new NoPricingRuleException(String.format("No pricing rule from Zone %s to Zone %s.", tripDto.getZoneFrom(), tripDto.getZoneTo()));
        }
    }

    /**
     * This method will calculate and set From and To Zones in the Trip
     *
     * @param pricingConfiguration Pricing configuration containing stations, zones and pricing rules details
     * @param tripDto              Trip details
     * @throws InvalidStationException this exception will be thrown when the Start or End stations is not defined in the configuration
     */
    private void setFromAndToZones(final PricingConfiguration pricingConfiguration, final TripDto tripDto) throws InvalidStationException {

        final Station startStation = validateAndGetStation(tripDto.getStationStart(), pricingConfiguration);
        final Station endStation = validateAndGetStation(tripDto.getStationEnd(), pricingConfiguration);
        final boolean isStartStationBeforeEndStation = startStation.before(endStation);
        tripDto.setZoneFrom(startStation.getSelectedZoneAsString(StationType.START, isStartStationBeforeEndStation));
        tripDto.setZoneTo(endStation.getSelectedZoneAsString(StationType.END, isStartStationBeforeEndStation));
    }

    /**
     * This method search for station details in the pricing configuration and return it
     *
     * @param stationId            station id
     * @param pricingConfiguration Pricing configuration containing stations, zones and pricing rules details
     * @return Station details
     * @throws InvalidStationException this exception will be thrown when the station is not defined in the configuration
     */
    private Station validateAndGetStation(final String stationId, final PricingConfiguration pricingConfiguration) throws InvalidStationException {
        final Optional<Station> stationOptional = pricingConfiguration.getStations().stream().filter(st -> st.getStationId().equals(stationId)).findFirst();
        if (!stationOptional.isPresent()) {
            throw new InvalidStationException(String.format("Invalid station : %s ", stationId));
        }
        return stationOptional.get();
    }
}
