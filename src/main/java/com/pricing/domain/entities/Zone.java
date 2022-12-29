package com.pricing.domain.entities;

import java.util.Objects;

public class Zone {

    private Integer zoneId;

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return zoneId.equals(zone.zoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId);
    }
}
