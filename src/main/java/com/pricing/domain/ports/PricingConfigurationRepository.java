package com.pricing.domain.ports;

import com.pricing.domain.entities.PricingConfiguration;

import java.io.IOException;

public interface PricingConfigurationRepository {
    PricingConfiguration loadPricingConfiguration() throws IOException;
}
