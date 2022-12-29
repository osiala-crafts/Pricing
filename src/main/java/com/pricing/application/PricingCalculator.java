package com.pricing.application;

import com.pricing.application.adapters.CommandLineAdapter;
import com.pricing.application.exeptions.ParseException;
import com.pricing.domain.ports.IPricingService;
import com.pricing.domain.ports.PricingConfigurationRepository;
import com.pricing.domain.services.PricingService;
import com.pricing.infrastructure.adapters.PricingConfigurationRepositoryFileAdapter;

import java.io.IOException;

public class PricingCalculator {

    public static void main(String[] args) throws IOException, ParseException {
        final String candidateInputFilePath = args[0];
        final String candidateOutputFilePath = args[1];

        // 1. Instantiate right-side adapter(s) ("I want to go outside the hexagon")
        PricingConfigurationRepository pricingConfigurationRepository = new PricingConfigurationRepositoryFileAdapter("conf/pricing_configuration.json");

        // 2. Instantiate the hexagon
        IPricingService pricingService = new PricingService(pricingConfigurationRepository);

        // 3. Instantiate the left-side adapter(s) ("I want ask/to go inside the hexagon")
        CommandLineAdapter commandLineAdapter = new CommandLineAdapter(pricingService);

        commandLineAdapter.processPricing(candidateInputFilePath, candidateOutputFilePath);
    }
}
