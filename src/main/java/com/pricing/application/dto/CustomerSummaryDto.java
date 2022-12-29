package com.pricing.application.dto;

import java.util.List;

public class CustomerSummaryDto {

    private String customerId;
    private String totalCostInCents;
    private List<TripDto> trips;

    public CustomerSummaryDto() {
    }

    public CustomerSummaryDto(String customerId, String totalCostInCents, List<TripDto> trips) {
        this.customerId = customerId;
        this.totalCostInCents = totalCostInCents;
        this.trips = trips;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTotalCostInCents() {
        return totalCostInCents;
    }

    public void setTotalCostInCents(String totalCostInCents) {
        this.totalCostInCents = totalCostInCents;
    }

    public List<TripDto> getTrips() {
        return trips;
    }

    public void setTrips(List<TripDto> trips) {
        this.trips = trips;
    }

}
