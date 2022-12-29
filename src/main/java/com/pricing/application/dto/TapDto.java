package com.pricing.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pricing.application.utils.UnixTimestampDeserializer;
import com.pricing.application.utils.UnixTimestampSerializer;

import java.time.Instant;

public class TapDto {
    @JsonProperty("unixTimestamp")
    @JsonSerialize(using = UnixTimestampSerializer.class)
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Instant tapDate;
    private Long customerId;
    private String station;

    public Instant getTapDate() {
        return tapDate;
    }

    public void setTapDate(Instant tapDate) {
        this.tapDate = tapDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
