package com.pricing.infrastructure.adapters;

import com.pricing.domain.entities.PricingConfiguration;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PricingConfigurationRepositoryFileAdapterTest {

    @Test
    void given_configuration_file_path_is_valid_and_json_file_content_is_valid_when_loadPricingConfiguration_then_return_pricingConfiguration() throws IOException {
        //given
        String pricingConfigurationFile = "conf/pricing_configuration_test.json";
        PricingConfigurationRepositoryFileAdapter pricingConfigurationRepositoryFileAdapter = new PricingConfigurationRepositoryFileAdapter(pricingConfigurationFile);
        //when
        PricingConfiguration pricingConfiguration = pricingConfigurationRepositoryFileAdapter.loadPricingConfiguration();

        //then
        assertNotNull(pricingConfiguration);
        assertNotNull(pricingConfiguration.getStations());
        assertEquals(9, pricingConfiguration.getStations().size());
        assertEquals(16, pricingConfiguration.getPricingRules().size());
    }

}