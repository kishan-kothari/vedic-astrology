package com.astrology.cli;

import com.astrology.planets.BirthData;
import com.astrology.core.AyanamshaType;
import com.astrology.core.EphemerisConfig;
import com.astrology.core.HouseSystem;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class BaseCommand implements Runnable {

    @Option(names = {"--date", "-d"}, required = true, description = "Birth date (yyyy-MM-dd)")
    protected String date;

    @Option(names = {"--time", "-t"}, required = true, description = "Birth time (HH:mm:ss)")
    protected String time;

    @Option(names = {"--tz"}, defaultValue = "UTC", description = "Timezone (e.g. Asia/Kolkata)")
    protected String timezone;

    @Option(names = {"--lat"}, required = true, description = "Latitude (e.g. 28.6139)")
    protected double latitude;

    @Option(names = {"--lon"}, required = true, description = "Longitude (e.g. 77.2090)")
    protected double longitude;

    @Option(names = {"--name", "-n"}, defaultValue = "Unknown", description = "Person's name")
    protected String name;

    @Option(names = {"--ayanamsha"}, defaultValue = "LAHIRI", description = "Ayanamsha type")
    protected String ayanamsha;

    @Option(names = {"--ephe"}, defaultValue = "./ephe", description = "Ephemeris path")
    protected String ephePath;

    protected BirthData buildBirthData() {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zoneId = ZoneId.of(timezone);
        AyanamshaType ayanamshaType;
        try {
            ayanamshaType = AyanamshaType.valueOf(ayanamsha.toUpperCase());
        } catch (IllegalArgumentException e) {
            ayanamshaType = AyanamshaType.LAHIRI;
        }

        return BirthData.builder()
            .name(name)
            .localDateTime(localDateTime)
            .timezone(zoneId)
            .latitude(latitude)
            .longitude(longitude)
            .ayanamshaType(ayanamshaType)
            .houseSystem(HouseSystem.WHOLE_SIGN)
            .build();
    }

    protected EphemerisConfig buildConfig() {
        AyanamshaType ayanamshaType;
        try {
            ayanamshaType = AyanamshaType.valueOf(ayanamsha.toUpperCase());
        } catch (IllegalArgumentException e) {
            ayanamshaType = AyanamshaType.LAHIRI;
        }
        return new EphemerisConfig(ephePath, ayanamshaType, HouseSystem.WHOLE_SIGN,
            de.thmac.swisseph.SweConst.SEFLG_SWIEPH | de.thmac.swisseph.SweConst.SEFLG_SPEED | de.thmac.swisseph.SweConst.SEFLG_SIDEREAL);
    }
}
