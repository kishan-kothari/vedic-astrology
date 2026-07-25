package com.astrology.dasha;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;
import com.astrology.planets.PlanetPosition;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.astrology.core.Planet.*;

public class YoginiDasha {

    public enum YoginiType {
        MANGALA("Mangala", MOON, 1.0),
        PINGALA("Pingala", SUN, 2.0),
        DHANYA("Dhanya", JUPITER, 3.0),
        BHRAMARI("Bhramari", MARS, 4.0),
        BHADRIKA("Bhadrika", MERCURY, 5.0),
        ULKA("Ulka", SATURN, 6.0),
        SIDDHA("Siddha", VENUS, 7.0),
        SANKATA("Sankata", RAHU, 8.0);

        private final String name;
        private final Planet lord;
        private final double years;

        YoginiType(String name, Planet lord, double years) {
            this.name = name;
            this.lord = lord;
            this.years = years;
        }

        public String getName() { return name; }
        public Planet getLord() { return lord; }
        public double getYears() { return years; }
    }

    public record YoginiDashaPeriod(
            YoginiType type,
            LocalDate startDate,
            LocalDate endDate,
            List<YoginiDashaPeriod> antardashas
    ) {}

    private static final YoginiType[] SEQUENCE = YoginiType.values();
    private static final double TOTAL_YEARS = 36.0;

    public List<YoginiDashaPeriod> calculateMahadashas(BirthChart chart, int cycles) {
        PlanetPosition moonPos = chart.getPlanet(MOON);
        double moonLon = moonPos.siderealLongitude();

        int nakshatraIndex = (int) Math.floor(moonLon / (360.0 / 27.0)); // 0-indexed (Ashwini=0)
        double elapsed = moonLon % (360.0 / 27.0);
        double remaining = (360.0 / 27.0) - elapsed;

        // Formula: (Nakshatra Number (1-27) + 3) % 8
        // Nakshatra Index is 0-26, so Number is nakshatraIndex + 1
        int formula = (nakshatraIndex + 1 + 3) % 8;
        int startIndex = formula == 0 ? 7 : formula - 1; // Map to 0-7 array index

        YoginiType startType = SEQUENCE[startIndex];
        double balanceYears = (remaining / (360.0 / 27.0)) * startType.getYears();

        LocalDate currentDate = chart.getBirthData().localDateTime().toLocalDate();
        List<YoginiDashaPeriod> mahadashas = new ArrayList<>();

        int currentIndex = startIndex;
        double mdYears = balanceYears;
        boolean isFirst = true;

        for (int i = 0; i < cycles * 8; i++) {
            YoginiType currentType = SEQUENCE[currentIndex];
            LocalDate endDate = addYears(currentDate, mdYears);
            
            List<YoginiDashaPeriod> antardashas = calculateAntardashas(currentType, mdYears, currentDate, endDate, isFirst, balanceYears);
            mahadashas.add(new YoginiDashaPeriod(currentType, currentDate, endDate, antardashas));

            currentDate = endDate;
            currentIndex = (currentIndex + 1) % 8;
            mdYears = SEQUENCE[currentIndex].getYears();
            isFirst = false;
        }

        return mahadashas;
    }

    private List<YoginiDashaPeriod> calculateAntardashas(YoginiType mahadasha, double mdYears, LocalDate mdStart, LocalDate mdEnd, boolean isFirst, double balanceYears) {
        List<YoginiDashaPeriod> result = new ArrayList<>();
        int startIndex = mahadasha.ordinal();
        
        LocalDate current = mdStart;
        double consumed = mahadasha.getYears() - balanceYears;
        
        int i = startIndex;
        int count = 0;
        
        while (count < 8) {
            YoginiType adType = SEQUENCE[i];
            double adYearsFull = (mahadasha.getYears() * adType.getYears()) / TOTAL_YEARS;
            
            double adYearsActual = adYearsFull;
            if (isFirst) {
                if (consumed >= adYearsFull) {
                    consumed -= adYearsFull;
                    i = (i + 1) % 8;
                    count++;
                    continue;
                } else if (consumed > 0) {
                    adYearsActual = adYearsFull - consumed;
                    consumed = 0;
                }
            }
            
            LocalDate next = addYears(current, adYearsActual);
            if (next.isAfter(mdEnd) || count == 7) next = mdEnd;
            
            result.add(new YoginiDashaPeriod(adType, current, next, null));
            current = next;
            
            i = (i + 1) % 8;
            count++;
        }
        return result;
    }

    private LocalDate addYears(LocalDate date, double yearsToAdd) {
        long days = Math.round(yearsToAdd * 365.2425);
        return date.plusDays(days);
    }
}
