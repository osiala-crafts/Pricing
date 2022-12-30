package com.pricing.application.dto;

import java.util.List;

public class CustomerSummaryDto {

    private Long customerId;
    private Integer totalCostInCents;
    private List<TripDto> trips;

    public CustomerSummaryDto() {
    }

    public CustomerSummaryDto(Long customerId, Integer totalCostInCents, List<TripDto> trips) {
        this.customerId = customerId;
        this.totalCostInCents = totalCostInCents;
        this.trips = trips;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getTotalCostInCents() {
        return totalCostInCents;
    }

    public void setTotalCostInCents(Integer totalCostInCents) {
        this.totalCostInCents = totalCostInCents;
    }

    public List<TripDto> getTrips() {
        return trips;
    }

    public void setTrips(List<TripDto> trips) {
        this.trips = trips;
    }

}
