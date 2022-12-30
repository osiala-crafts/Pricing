package com.pricing.application.dto;

public class TripDto {

    private String stationStart;
    private String stationEnd;
    private Long startedJourneyAt;
    private Integer costInCents;
    private Integer zoneFrom;
    private Integer zoneTo;

    public TripDto() {
    }

    public TripDto(String stationStart, String stationEnd, Long startedJourneyAt) {
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

    public Long getStartedJourneyAt() {
        return startedJourneyAt;
    }

    public void setStartedJourneyAt(Long startedJourneyAt) {
        this.startedJourneyAt = startedJourneyAt;
    }

    public Integer getCostInCents() {
        return costInCents;
    }

    public void setCostInCents(Integer costInCents) {
        this.costInCents = costInCents;
    }

    public Integer getZoneFrom() {
        return zoneFrom;
    }

    public void setZoneFrom(Integer zoneFrom) {
        this.zoneFrom = zoneFrom;
    }

    public Integer getZoneTo() {
        return zoneTo;
    }

    public void setZoneTo(Integer zoneTo) {
        this.zoneTo = zoneTo;
    }
}
