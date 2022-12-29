package com.pricing.domain.entities;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;

public class Station {

    private String stationId;
    private Set<Zone> zones;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Set<Zone> getZones() {
        return zones;
    }

    public void setZones(Set<Zone> zones) {
        this.zones = zones;
    }

    public boolean before(Station station) {
        return station.getStationId().compareTo(this.stationId) > 0;
    }

    private boolean isBoundary() {
        return this.zones.size() > 1;
    }

    public String getSelectedZoneAsString(StationType stationType, boolean isStartStationBeforeEndStation) {

        if (!isBoundary()) {
            return String.valueOf(this.zones.iterator().next().getZoneId());
        }
        final OptionalInt zoneIdOptional;
        if ((StationType.START == stationType && isStartStationBeforeEndStation) || (StationType.END == stationType && !isStartStationBeforeEndStation)) {
            zoneIdOptional = this.zones.stream().mapToInt(Zone::getZoneId).max();
        } else {
            zoneIdOptional = this.zones.stream().mapToInt(Zone::getZoneId).min();
        }
        if (zoneIdOptional.isPresent()) {
            return String.valueOf(zoneIdOptional.getAsInt());
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return stationId.equals(station.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId);
    }
}
