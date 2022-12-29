package com.pricing.domain.entities;

import java.util.Objects;

public class PricingRule {

    private Zone departureZone;
    private Zone arrivalZone;
    private Integer price;

    public Zone getDepartureZone() {
        return departureZone;
    }

    public void setDepartureZone(Zone departureZone) {
        this.departureZone = departureZone;
    }

    public Zone getArrivalZone() {
        return arrivalZone;
    }

    public void setArrivalZone(Zone arrivalZone) {
        this.arrivalZone = arrivalZone;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricingRule that = (PricingRule) o;
        return departureZone.equals(that.departureZone) &&
                arrivalZone.equals(that.arrivalZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureZone, arrivalZone);
    }
}
