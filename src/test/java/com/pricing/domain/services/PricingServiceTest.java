package com.pricing.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.application.dto.CandidateInputDto;
import com.pricing.application.dto.CandidateOutputDto;
import com.pricing.application.dto.TapDto;
import com.pricing.application.dto.TripDto;
import com.pricing.domain.entities.PricingConfiguration;
import com.pricing.domain.ports.PricingConfigurationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

class PricingServiceTest {

    @InjectMocks
    private PricingService pricingService;

    @Mock
    PricingConfigurationRepository pricingConfigurationRepository;

    @BeforeEach
    void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        Mockito.when(pricingConfigurationRepository.loadPricingConfiguration()).thenReturn(buildPricingConfiguration());
    }

    private PricingConfiguration buildPricingConfiguration() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("conf/pricing_configuration_test.json");
        if (resource != null) {
            return new ObjectMapper().readValue(resource, PricingConfiguration.class);
        }
        return new PricingConfiguration();
    }

    @Test
    void given_candidate_input_dto_is_null_when_price_then_return_empty_candidate_output_dto() throws IOException {
        CandidateOutputDto candidateOutputDto = pricingService.price(null);
        Assertions.assertNull(candidateOutputDto.getCustomerSummaries());
    }

    @Test
    @DisplayName("Testing non boundary start and end stations in the right direction use case")
    void given_start_station_A_end_station_D_which_are_not_boundary_when_price_then_startZone_is_1_and_endZone_is_2_and_cost_is_240() throws IOException {
        //given
        CandidateInputDto candidateInputDto = prepareSingleJourneyCandidateInput();
        candidateInputDto.getTaps().get(0).setStation("A");
        candidateInputDto.getTaps().get(1).setStation("D");

        //when
        CandidateOutputDto candidateOutputDto = pricingService.price(candidateInputDto);

        //then
        assertThereIsOneTripInCandidateOutput(candidateOutputDto);
        final TripDto trip = candidateOutputDto.getCustomerSummaries().get(0).getTrips().get(0);

        Assertions.assertEquals(1, trip.getZoneFrom());
        Assertions.assertEquals(2, trip.getZoneTo());
        Assertions.assertEquals(240, trip.getCostInCents());
    }

    @Test
    @DisplayName("Testing non boundary start and end stations in the opposite direction use case")
    void given_start_station_D_end_station_A_which_are_not_boundary_when_price_then_startZone_is_2_and_endZone_is_1_and_cost_is_240() throws IOException {
        //given
        CandidateInputDto candidateInputDto = prepareSingleJourneyCandidateInput();
        candidateInputDto.getTaps().get(0).setStation("D");
        candidateInputDto.getTaps().get(1).setStation("A");

        //when
        CandidateOutputDto candidateOutputDto = pricingService.price(candidateInputDto);

        //then
        assertThereIsOneTripInCandidateOutput(candidateOutputDto);
        final TripDto trip = candidateOutputDto.getCustomerSummaries().get(0).getTrips().get(0);

        Assertions.assertEquals(2, trip.getZoneFrom());
        Assertions.assertEquals(1, trip.getZoneTo());
        Assertions.assertEquals(240, trip.getCostInCents());
    }

    @Test
    @DisplayName("Testing boundary start station (C) in the right direction --> lowest fare 3 -> 4")
    void given_start_station_boundary_C_end_station_G_not_boundary_when_price_then_startZone_is_3_and_endZone_is_4_and_cost_is_200() throws IOException {
        //given
        CandidateInputDto candidateInputDto = prepareSingleJourneyCandidateInput();
        candidateInputDto.getTaps().get(0).setStation("C");
        candidateInputDto.getTaps().get(1).setStation("G");

        //when
        CandidateOutputDto candidateOutputDto = pricingService.price(candidateInputDto);

        //then
        assertThereIsOneTripInCandidateOutput(candidateOutputDto);
        final TripDto trip = candidateOutputDto.getCustomerSummaries().get(0).getTrips().get(0);

        Assertions.assertEquals(3, trip.getZoneFrom());
        Assertions.assertEquals(4, trip.getZoneTo());
        Assertions.assertEquals(200, trip.getCostInCents());
    }

    @Test
    @DisplayName("Testing boundary start (C) station in the opposite direction --> lowest fare 2 -> 1")
    void given_start_station_boundary_C_end_station_A_not_boundary_when_price_then_startZone_is_2_and_endZone_is_1_and_cost_is_240() throws IOException {
        //given
        CandidateInputDto candidateInputDto = prepareSingleJourneyCandidateInput();
        candidateInputDto.getTaps().get(0).setStation("C");
        candidateInputDto.getTaps().get(1).setStation("A");

        //when
        CandidateOutputDto candidateOutputDto = pricingService.price(candidateInputDto);

        //then
        assertThereIsOneTripInCandidateOutput(candidateOutputDto);
        final TripDto trip = candidateOutputDto.getCustomerSummaries().get(0).getTrips().get(0);

        Assertions.assertEquals(2, trip.getZoneFrom());
        Assertions.assertEquals(1, trip.getZoneTo());
        Assertions.assertEquals(240, trip.getCostInCents());
    }

    @Test
    @DisplayName("Testing boundary end station (C) in the right direction  --> lowest fare 1 -> 2")
    void given_start_station_not_boundary_A_end_station_C_boundary_when_price_then_startZone_is_1_and_endZone_is_2_and_cost_is_240() throws IOException {
        //given
        CandidateInputDto candidateInputDto = prepareSingleJourneyCandidateInput();
        candidateInputDto.getTaps().get(0).setStation("A");
        candidateInputDto.getTaps().get(1).setStation("C");

        //when
        CandidateOutputDto candidateOutputDto = pricingService.price(candidateInputDto);

        //then
        assertThereIsOneTripInCandidateOutput(candidateOutputDto);
        final TripDto trip = candidateOutputDto.getCustomerSummaries().get(0).getTrips().get(0);

        Assertions.assertEquals(1, trip.getZoneFrom());
        Assertions.assertEquals(2, trip.getZoneTo());
        Assertions.assertEquals(240, trip.getCostInCents());
    }

    @Test
    @DisplayName("Testing boundary end station (C) in the opposite direction  --> lowest fare 4 -> 3")
    void given_start_station_not_boundary_G_end_station_C_boundary_when_price_then_startZone_is_4_and_endZone_is_3_and_cost_is_200() throws IOException {
        //given
        CandidateInputDto candidateInputDto = prepareSingleJourneyCandidateInput();
        candidateInputDto.getTaps().get(0).setStation("G");
        candidateInputDto.getTaps().get(1).setStation("C");

        //when
        CandidateOutputDto candidateOutputDto = pricingService.price(candidateInputDto);

        //then
        assertThereIsOneTripInCandidateOutput(candidateOutputDto);
        final TripDto trip = candidateOutputDto.getCustomerSummaries().get(0).getTrips().get(0);

        Assertions.assertEquals(4, trip.getZoneFrom());
        Assertions.assertEquals(3, trip.getZoneTo());
        Assertions.assertEquals(200, trip.getCostInCents());

    }

    @Test
    @DisplayName("Testing boundary start (C) and end (F) stations in the right direction use case --> lowest fare 3 -> 3")
    void given_start_station_boundary_C_end_station_F_boundary_when_price_then_startZone_is_3_and_endZone_is_3_and_cost_is_200() throws IOException {
        //given
        CandidateInputDto candidateInputDto = prepareSingleJourneyCandidateInput();
        candidateInputDto.getTaps().get(0).setStation("C");
        candidateInputDto.getTaps().get(1).setStation("F");

        //when
        CandidateOutputDto candidateOutputDto = pricingService.price(candidateInputDto);

        //then
        assertThereIsOneTripInCandidateOutput(candidateOutputDto);
        final TripDto trip = candidateOutputDto.getCustomerSummaries().get(0).getTrips().get(0);

        Assertions.assertEquals(3, trip.getZoneFrom());
        Assertions.assertEquals(3, trip.getZoneTo());
        Assertions.assertEquals(200, trip.getCostInCents());
    }

    @Test
    @DisplayName("Testing boundary start (F) and end (C) stations in the opposite direction use case --> lowest fare 3 -> 3")
    void given_start_station_boundary_F_end_station_C_boundary_when_price_then_startZone_is_3_and_endZone_is_3_and_cost_is_280() throws IOException {
        //given
        CandidateInputDto candidateInputDto = prepareSingleJourneyCandidateInput();
        candidateInputDto.getTaps().get(0).setStation("F");
        candidateInputDto.getTaps().get(1).setStation("C");

        //when
        CandidateOutputDto candidateOutputDto = pricingService.price(candidateInputDto);

        //then
        assertThereIsOneTripInCandidateOutput(candidateOutputDto);
        final TripDto trip = candidateOutputDto.getCustomerSummaries().get(0).getTrips().get(0);

        Assertions.assertEquals(3, trip.getZoneFrom());
        Assertions.assertEquals(3, trip.getZoneTo());
        Assertions.assertEquals(200, trip.getCostInCents());
    }

    private CandidateInputDto prepareSingleJourneyCandidateInput() {
        CandidateInputDto candidateInputDto = new CandidateInputDto();
        TapDto tapDto11 = new TapDto();
        tapDto11.setCustomerId(1L);
        tapDto11.setStation("A");
        tapDto11.setUnixTimestamp(1L);

        TapDto tapDto12 = new TapDto();
        tapDto12.setCustomerId(1L);
        tapDto12.setStation("D");
        tapDto12.setUnixTimestamp(2L);
        candidateInputDto.setTaps(Arrays.asList(tapDto11, tapDto12));
        return candidateInputDto;
    }

    private void assertThereIsOneTripInCandidateOutput(CandidateOutputDto candidateOutputDto) {
        Assertions.assertNotNull(candidateOutputDto);
        Assertions.assertNotNull(candidateOutputDto.getCustomerSummaries());
        Assertions.assertEquals(1, candidateOutputDto.getCustomerSummaries().size());
        Assertions.assertNotNull(candidateOutputDto.getCustomerSummaries().get(0).getTrips());
        Assertions.assertEquals(1, candidateOutputDto.getCustomerSummaries().get(0).getTrips().size());
    }


}
