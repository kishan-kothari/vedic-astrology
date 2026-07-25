package com.astrology.dasha;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.planets.BirthChart;
import com.astrology.planets.PlanetPosition;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.astrology.dasha.DashaPeriod.DashaType.*;

public class JaiminiCharaDasha {

    public List<DashaPeriod> calculateCharaDashas(BirthChart chart, int yearsAhead) {
        Rashi lagna = chart.getLagnaRashi();
        boolean forward = isForwardSequence(lagna);
        
        List<DashaPeriod> dashas = new ArrayList<>();
        LocalDate currentDate = chart.getBirthData().localDateTime().toLocalDate();
        LocalDate endDateLimit = currentDate.plusYears(yearsAhead);
        
        int currentSignOrdinal = lagna.ordinal();
        
        while (currentDate.isBefore(endDateLimit)) {
            Rashi sign = Rashi.values()[currentSignOrdinal];
            int durationYears = calculateSignDuration(sign, chart);
            
            LocalDate endDate = currentDate.plusYears(durationYears);
            
            // For sign-based dashas, the "lord" planet field can hold the lord of the sign,
            // or we could overload it. The prompt specifies DashaPeriod has Planet lord.
            Planet signLord = getLordOfSign(sign);
            List<DashaPeriod> subPeriods = calculateSubPeriods(sign, durationYears, currentDate);
            
            dashas.add(new DashaPeriod(signLord, currentDate, endDate, ChronoUnit.DAYS.between(currentDate, endDate), subPeriods, MAHADASHA));
            
            currentDate = endDate;
            if (forward) {
                currentSignOrdinal = (currentSignOrdinal + 1) % 12;
            } else {
                currentSignOrdinal = (currentSignOrdinal + 11) % 12;
            }
        }
        
        return dashas;
    }
    
    private boolean isForwardSequence(Rashi lagna) {
        return lagna.ordinal() % 2 == 0; // Just a simplified logic for Chara dasha sequence
    }
    
    private int calculateSignDuration(Rashi sign, BirthChart chart) {
        Planet lord = getLordOfSign(sign);
        PlanetPosition lordPos = chart.getPlanet(lord);
        
        if (lordPos.rashi() == sign) {
            return 12;
        }
        
        int diff = Math.abs(lordPos.rashi().ordinal() - sign.ordinal());
        if (isForwardSequence(sign)) {
            diff = (lordPos.rashi().ordinal() - sign.ordinal() + 12) % 12;
        } else {
            diff = (sign.ordinal() - lordPos.rashi().ordinal() + 12) % 12;
        }
        
        return diff == 0 ? 12 : diff;
    }
    
    public List<DashaPeriod> calculateSubPeriods(Rashi sign, int totalYears, LocalDate start) {
        List<DashaPeriod> result = new ArrayList<>();
        boolean forward = isForwardSequence(sign);
        
        LocalDate current = start;
        int currentSignOrdinal = sign.ordinal();
        
        for (int i = 0; i < 12; i++) {
            Rashi subSign = Rashi.values()[currentSignOrdinal];
            Planet subLord = getLordOfSign(subSign);
            
            // Sub-period duration = totalYears months
            LocalDate next = current.plusMonths(totalYears);
            result.add(new DashaPeriod(subLord, current, next, ChronoUnit.DAYS.between(current, next), null, ANTARDASHA));
            
            current = next;
            if (forward) {
                currentSignOrdinal = (currentSignOrdinal + 1) % 12;
            } else {
                currentSignOrdinal = (currentSignOrdinal + 11) % 12;
            }
        }
        return result;
    }
    
    public Planet calculateAtmakaraka(BirthChart chart) {
        Planet ak = null;
        double maxDegree = -1;
        for (Map.Entry<Planet, PlanetPosition> entry : chart.getPositions().entrySet()) {
            if (entry.getKey().isShadow()) continue;
            double degree = entry.getValue().degreeInSign();
            if (degree > maxDegree) {
                maxDegree = degree;
                ak = entry.getKey();
            }
        }
        return ak;
    }
    
    private Planet getLordOfSign(Rashi sign) {
        return switch (sign) {
            case ARIES, SCORPIO -> Planet.MARS;
            case TAURUS, LIBRA -> Planet.VENUS;
            case GEMINI, VIRGO -> Planet.MERCURY;
            case CANCER -> Planet.MOON;
            case LEO -> Planet.SUN;
            case SAGITTARIUS, PISCES -> Planet.JUPITER;
            case CAPRICORN, AQUARIUS -> Planet.SATURN;
        };
    }
}
