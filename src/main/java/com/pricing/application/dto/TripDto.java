package com.pricing.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pricing.application.utils.UnixTimestampSerializer;

import java.time.Instant;

public class TripDto {

    private String stationStart;
    private String stationEnd;
    @JsonSerialize(using = UnixTimestampSerializer.class)
    private Instant startedJourneyAt;
    private Integer costInCents;
    private String zoneFrom;
    private String zoneTo;

    public TripDto() {
    }

    public TripDto(String stationStart, String stationEnd, Instant startedJourneyAt) {
        this.stationStart = stationStart;
        this.stationEnd = stationEnd;
        this.startedJourneyAt = startedJourneyAt;
    }

    public String getStationStart() {
        return stationStart;
    }

    public void setStationStart(String stationStart) {
        this.stationStart = stationStart;
    }

    public String getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(String stationEnd) {
        this.stationEnd = stationEnd;
    }

    public Instant getStartedJourneyAt() {
        return startedJourneyAt;
    }

    public void setStartedJourneyAt(Instant startedJourneyAt) {
        this.startedJourneyAt = startedJourneyAt;
    }

    public Integer getCostInCents() {
        return costInCents;
    }

    public void setCostInCents(Integer costInCents) {
        this.costInCents = costInCents;
    }

    public String getZoneFrom() {
        return zoneFrom;
    }

    public void setZoneFrom(String zoneFrom) {
        this.zoneFrom = zoneFrom;
    }

    public String getZoneTo() {
        return zoneTo;
    }

    public void setZoneTo(String zoneTo) {
        this.zoneTo = zoneTo;
    }
}
