package com.pricing.application.dto;

import java.io.Serializable;
import java.util.List;

public class CandidateInputDto implements Serializable {

    private List<TapDto> taps;

    public List<TapDto> getTaps() {
        return taps;
    }

    public void setTaps(List<TapDto> taps) {
        this.taps = taps;
    }
}
