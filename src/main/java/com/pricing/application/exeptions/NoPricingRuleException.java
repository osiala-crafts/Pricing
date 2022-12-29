package com.pricing.application.exeptions;

public class NoPricingRuleException extends Exception {
    public NoPricingRuleException(String message) {
        super(message);
    }
}
