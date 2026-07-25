package com.astrology.transit;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.planets.BirthChart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SadeSatiCalculator {

    public record SadeSatiPhase(String phaseName, Rashi saturnSign, LocalDate startDate, LocalDate endDate, String type) {}

    /**
     * Approximates Sade Sati phases for 120 years.
     * Actual implementation should use SwissEphemeris to find exact ingress dates.
     */
    public static List<SadeSatiPhase> calculateSadeSati(BirthChart chart) {
        List<SadeSatiPhase> phases = new ArrayList<>();
        Rashi moonSign = Rashi.fromNumber(chart.getHouseOfPlanet(Planet.MOON));
        
        // Sade Sati occurs when Saturn is in 12th, 1st, and 2nd from Moon
        Rashi twelfth = Rashi.fromNumber(moonSign.getNumber() - 1);
        Rashi first = moonSign;
        Rashi second = Rashi.fromNumber(moonSign.getNumber() + 1);

        LocalDate birthDate = chart.getBirthData().localDateTime().toLocalDate();
        
        // Approximate: Saturn takes 29.5 years per cycle, ~2.46 years per sign
        // Find natal Saturn distance from the 12th sign from Moon
        Rashi saturnSign = Rashi.fromNumber(chart.getHouseOfPlanet(Planet.SATURN));
        int signsToSadeSati = (twelfth.getNumber() - saturnSign.getNumber() + 12) % 12;
        
        // If it's already past (e.g., saturn in 1st or 2nd), adjust
        if (saturnSign == first) signsToSadeSati = -1;
        if (saturnSign == second) signsToSadeSati = -2;

        double yearsToFirstSadeSati = signsToSadeSati * 2.46;
        if (yearsToFirstSadeSati < 0) yearsToFirstSadeSati += 29.5;

        for (int cycle = 0; cycle < 4; cycle++) {
            double startOffset = yearsToFirstSadeSati + (cycle * 29.5);
            LocalDate cycleStart = birthDate.plusDays((long)(startOffset * 365.25));
            
            phases.add(new SadeSatiPhase("Rising", twelfth, cycleStart, cycleStart.plusDays(898), "Small Panoti"));
            phases.add(new SadeSatiPhase("Peak", first, cycleStart.plusDays(898), cycleStart.plusDays(1796), "Sade Sati"));
            phases.add(new SadeSatiPhase("Setting", second, cycleStart.plusDays(1796), cycleStart.plusDays(2694), "Sade Sati"));
        }

        return phases;
    }
}
