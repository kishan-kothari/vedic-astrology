package com.astrology.dasha;

import com.astrology.core.Planet;
import java.time.LocalDate;
import java.util.List;

public record DashaPeriod(
    Planet lord,
    LocalDate startDate,
    LocalDate endDate,
    long durationDays,
    List<DashaPeriod> subPeriods,
    DashaType type
) {
    public enum DashaType { MAHADASHA, ANTARDASHA, PRATYANTARDASHA, SOOKSHMA, PRANA }
    
    public boolean isActive(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    
    public String toFormattedString() {
        return String.format("%s %s (%s to %s)", lord, type, startDate, endDate);
    }
    
    public double getYearDuration() {
        return durationDays / 365.25;
    }
}
