package com.pricing.domain.entities;

import java.util.Set;

public class PricingConfiguration {

    private Set<Station> stations;
    private Set<PricingRule> pricingRules;

    public Set<Station> getStations() {
        return stations;
    }

    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }

    public Set<PricingRule> getPricingRules() {
        return pricingRules;
    }

    public void setPricingRules(Set<PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
    }
}
