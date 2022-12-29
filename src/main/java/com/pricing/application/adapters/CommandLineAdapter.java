package com.pricing.application.adapters;

import com.pricing.application.dto.CandidateInputDto;
import com.pricing.application.dto.CandidateOutputDto;
import com.pricing.application.exeptions.ParseException;
import com.pricing.application.utils.InputOutputParser;
import com.pricing.domain.ports.IPricingService;

import java.io.IOException;

public class CommandLineAdapter {

    private IPricingService pricingService;

    public CommandLineAdapter(IPricingService pricingService) {
        this.pricingService = pricingService;
    }

    public void processPricing(String candidateInputFilePath, String candidateOutputFilePath) throws ParseException, IOException {
        CandidateInputDto candidateInputDto = InputOutputParser.readCandidateInput(candidateInputFilePath);
        CandidateOutputDto candidateOutputDto = this.pricingService.price(candidateInputDto);
        InputOutputParser.writeCandidateOutput(candidateOutputFilePath, candidateOutputDto);
    }
}
