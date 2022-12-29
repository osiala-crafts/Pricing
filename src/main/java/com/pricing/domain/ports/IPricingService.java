package com.pricing.domain.ports;

import com.pricing.application.dto.CandidateInputDto;
import com.pricing.application.dto.CandidateOutputDto;

import java.io.IOException;

public interface IPricingService {
    CandidateOutputDto price(CandidateInputDto candidateInputDto) throws IOException;
}
