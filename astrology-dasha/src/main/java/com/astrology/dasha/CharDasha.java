package com.astrology.dasha;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.planets.BirthChart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CharDasha {

    public record CharDashaPeriod(
            Rashi rashi,
            LocalDate startDate,
            LocalDate endDate,
            List<CharDashaPeriod> antardashas
    ) {}

    public List<CharDashaPeriod> calculateMahadashas(BirthChart chart) {
        List<CharDashaPeriod> result = new ArrayList<>();
        Rashi lagna = chart.getLagnaRashi();
        LocalDate currentDate = chart.getBirthData().localDateTime().toLocalDate();

        // Determine Sequence Direction (based on 9th house from Lagna)
        int ninthHouseRashi = ((lagna.getNumber() - 1 + 8) % 12) + 1;
        boolean isForwardSequence = (ninthHouseRashi % 2 != 0);

        int currentRashiNum = lagna.getNumber();

        for (int i = 0; i < 12; i++) {
            Rashi currentRashi = Rashi.fromNumber(currentRashiNum);
            int years = calculateDashaYears(currentRashi, chart);
            
            LocalDate endDate = addYears(currentDate, years);
            List<CharDashaPeriod> antardashas = calculateAntardashas(currentRashi, years, currentDate, endDate, chart);
            
            result.add(new CharDashaPeriod(currentRashi, currentDate, endDate, antardashas));

            currentDate = endDate;
            if (isForwardSequence) {
                currentRashiNum = currentRashiNum == 12 ? 1 : currentRashiNum + 1;
            } else {
                currentRashiNum = currentRashiNum == 1 ? 12 : currentRashiNum - 1;
            }
        }

        return result;
    }

    private List<CharDashaPeriod> calculateAntardashas(Rashi mahadashaRashi, int mdYears, LocalDate mdStart, LocalDate mdEnd, BirthChart chart) {
        List<CharDashaPeriod> result = new ArrayList<>();
        
        // Antardasha sequence starts from the 2nd house of Mahadasha Rashi (or 12th if backward)
        boolean isForward = (mahadashaRashi.getNumber() == 1 || mahadashaRashi.getNumber() == 2 || mahadashaRashi.getNumber() == 3 ||
                             mahadashaRashi.getNumber() == 7 || mahadashaRashi.getNumber() == 8 || mahadashaRashi.getNumber() == 9);
        
        int currentAdNum;
        if (isForward) {
            currentAdNum = mahadashaRashi.getNumber() == 12 ? 1 : mahadashaRashi.getNumber() + 1;
        } else {
            currentAdNum = mahadashaRashi.getNumber() == 1 ? 12 : mahadashaRashi.getNumber() - 1;
        }

        double monthsPerAd = mdYears; // 1 year of MD = 1 month of AD
        LocalDate current = mdStart;

        for (int i = 0; i < 12; i++) {
            Rashi adRashi = Rashi.fromNumber(currentAdNum);
            LocalDate next = addMonths(current, monthsPerAd);
            if (i == 11) next = mdEnd; // Correct rounding errors
            
            result.add(new CharDashaPeriod(adRashi, current, next, null));
            current = next;
            
            if (isForward) {
                currentAdNum = currentAdNum == 12 ? 1 : currentAdNum + 1;
            } else {
                currentAdNum = currentAdNum == 1 ? 12 : currentAdNum - 1;
            }
        }
        return result;
    }

    private int calculateDashaYears(Rashi rashi, BirthChart chart) {
        Planet lord = rashi.getLord();
        
        // Handle dual lordship for Scorpio and Aquarius
        if (rashi.getNumber() == 8) {
            lord = getStrongerLord(Planet.MARS, Planet.KETU, chart);
        } else if (rashi.getNumber() == 11) {
            lord = getStrongerLord(Planet.SATURN, Planet.RAHU, chart);
        }

        int lordHouse = chart.getHouseOfPlanet(lord); // This is 1-indexed relative to Lagna
        // We need the Rashi number of the lord's position
        int lordRashiNum = ((chart.getLagnaRashi().getNumber() - 1 + lordHouse - 1) % 12) + 1;

        if (rashi.getNumber() == lordRashiNum) {
            return 12;
        }

        boolean countForward = (rashi.getNumber() == 1 || rashi.getNumber() == 2 || rashi.getNumber() == 3 ||
                                rashi.getNumber() == 7 || rashi.getNumber() == 8 || rashi.getNumber() == 9);

        int count;
        if (countForward) {
            count = lordRashiNum - rashi.getNumber();
            if (count < 0) count += 12;
            count += 1; // inclusive
        } else {
            count = rashi.getNumber() - lordRashiNum;
            if (count < 0) count += 12;
            count += 1; // inclusive
        }

        return count - 1;
    }

    private Planet getStrongerLord(Planet p1, Planet p2, BirthChart chart) {
        // Simplified strength for dual lords: 
        // If one is with more planets, it's stronger.
        int house1 = chart.getHouseOfPlanet(p1);
        int house2 = chart.getHouseOfPlanet(p2);
        
        int p1Count = countPlanetsInHouse(house1, chart);
        int p2Count = countPlanetsInHouse(house2, chart);
        
        if (p1Count > p2Count) return p1;
        if (p2Count > p1Count) return p2;
        
        // If tie, check which is exalted or own sign (Simplified: just return p2 for Rahu/Ketu preference usually in Jaimini if tied)
        return p2;
    }

    private int countPlanetsInHouse(int houseNum, BirthChart chart) {
        int count = 0;
        for (Planet p : Planet.values()) {
            if (p == Planet.GULIKA || p == Planet.MANDI) continue;
            if (chart.getHouseOfPlanet(p) == houseNum) count++;
        }
        return count;
    }

    private LocalDate addYears(LocalDate date, int yearsToAdd) {
        return date.plusYears(yearsToAdd);
    }
    
    private LocalDate addMonths(LocalDate date, double monthsToAdd) {
        long days = Math.round(monthsToAdd * 30.436875);
        return date.plusDays(days);
    }
}
