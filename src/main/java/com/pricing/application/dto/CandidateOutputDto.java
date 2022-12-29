package com.pricing.application.dto;

import java.util.List;

public class CandidateOutputDto {

    private List<CustomerSummaryDto> customerSummaries;

    public CandidateOutputDto() {
    }

    public CandidateOutputDto(List<CustomerSummaryDto> customerSummaries) {
        this.customerSummaries = customerSummaries;
    }

    public List<CustomerSummaryDto> getCustomerSummaries() {
        return customerSummaries;
    }

    public void setCustomerSummaries(List<CustomerSummaryDto> customerSummaries) {
        this.customerSummaries = customerSummaries;
    }
}
