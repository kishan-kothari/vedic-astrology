package com.astrology.planets;

import com.astrology.core.AyanamshaType;
import com.astrology.core.HouseSystem;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

public record BirthData(
    String name,
    LocalDateTime localDateTime,
    ZoneId timezone,
    double latitude,
    double longitude,
    AyanamshaType ayanamshaType,
    HouseSystem houseSystem
) {
    public LocalDateTime toUtc() {
        return localDateTime.atZone(timezone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }
    
    public double toJulianDayUT() {
        ZonedDateTime utc = localDateTime.atZone(timezone).withZoneSameInstant(ZoneId.of("UTC"));
        int y = utc.getYear();
        int m = utc.getMonthValue();
        int d = utc.getDayOfMonth();
        double h = utc.getHour() + utc.getMinute() / 60.0 + utc.getSecond() / 3600.0;
        
        if (m <= 2) {
            y -= 1;
            m += 12;
        }
        int a = y / 100;
        int b = 2 - a + (a / 4);
        return Math.floor(365.25 * (y + 4716)) + Math.floor(30.6001 * (m + 1)) + d + h / 24.0 + b - 1524.5;
    }
    
    public double toJulianDayET() {
        double jdUt = toJulianDayUT();
        // Delta T approximation (DT in days)
        double deltaT = 69.0 / 86400.0; // Simplified
        return jdUt + deltaT;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String name;
        private LocalDateTime localDateTime;
        private ZoneId timezone;
        private double latitude;
        private double longitude;
        private AyanamshaType ayanamshaType = AyanamshaType.LAHIRI;
        private HouseSystem houseSystem = HouseSystem.WHOLE_SIGN;
        
        public Builder name(String name) { this.name = name; return this; }
        public Builder localDateTime(LocalDateTime localDateTime) { this.localDateTime = localDateTime; return this; }
        public Builder timezone(ZoneId timezone) { this.timezone = timezone; return this; }
        public Builder latitude(double latitude) { this.latitude = latitude; return this; }
        public Builder longitude(double longitude) { this.longitude = longitude; return this; }
        public Builder ayanamshaType(AyanamshaType ayanamshaType) { this.ayanamshaType = ayanamshaType; return this; }
        public Builder houseSystem(HouseSystem houseSystem) { this.houseSystem = houseSystem; return this; }
        
        public BirthData build() {
            return new BirthData(name, localDateTime, timezone, latitude, longitude, ayanamshaType, houseSystem);
        }
    }
}
