package com.pricing.infrastructure.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.domain.entities.PricingConfiguration;
import com.pricing.domain.ports.PricingConfigurationRepository;

import java.io.IOException;
import java.net.URL;

public class PricingConfigurationRepositoryFileAdapter implements PricingConfigurationRepository {

    private String filePath;

    public PricingConfigurationRepositoryFileAdapter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public PricingConfiguration loadPricingConfiguration() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(this.filePath);
        if (resource != null) {
            return new ObjectMapper().readValue(resource, PricingConfiguration.class);
        }
        return null;
    }
}
