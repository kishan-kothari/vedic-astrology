package com.astrology.strength;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;
import java.util.Set;

public class DrikBala {

    public static double calculate(Planet planet, BirthChart chart) {
        if (planet == Planet.RAHU || planet == Planet.KETU) return 0.0;
        
        double totalDrik = 0.0;
        for (Planet p : Planet.values()) {
            if (p == planet || p.isShadow()) continue;
            totalDrik += getAspectStrength(p, planet, chart);
        }
        return totalDrik;
    }

    public static double getAspectStrength(Planet source, Planet target, BirthChart chart) {
        double sourceLong = chart.getPositions().get(source).siderealLongitude();
        double targetLong = chart.getPositions().get(target).siderealLongitude();
        
        double diff = (targetLong - sourceLong + 360) % 360;
        
        double strength = 0.0;
        // Standard 7th aspect
        if (diff >= 150 && diff <= 210) {
            double orb = Math.abs(diff - 180);
            strength = Math.max(0, 60.0 - (orb * 2));
        }
        
        // Special aspects
        if (source == Planet.MARS) {
            if (diff >= 60 && diff <= 120) strength = Math.max(strength, Math.max(0, 60.0 - Math.abs(diff - 90) * 2)); // 4th
            if (diff >= 180 && diff <= 240) strength = Math.max(strength, Math.max(0, 60.0 - Math.abs(diff - 210) * 2)); // 8th
        } else if (source == Planet.JUPITER) {
            if (diff >= 90 && diff <= 150) strength = Math.max(strength, Math.max(0, 60.0 - Math.abs(diff - 120) * 2)); // 5th
            if (diff >= 210 && diff <= 270) strength = Math.max(strength, Math.max(0, 60.0 - Math.abs(diff - 240) * 2)); // 9th
        } else if (source == Planet.SATURN) {
            if (diff >= 30 && diff <= 90) strength = Math.max(strength, Math.max(0, 60.0 - Math.abs(diff - 60) * 2)); // 3rd
            if (diff >= 240 && diff <= 300) strength = Math.max(strength, Math.max(0, 60.0 - Math.abs(diff - 270) * 2)); // 10th
        }

        Set<Planet> benefics = Set.of(Planet.JUPITER, Planet.VENUS, Planet.MERCURY, Planet.MOON);
        if (!benefics.contains(source)) {
            strength = -strength;
        }
        
        return strength;
    }

    private static int[] getAspectedHouses(Planet planet) {
        return switch (planet) {
            case MARS -> new int[]{4, 7, 8};
            case JUPITER -> new int[]{5, 7, 9};
            case SATURN -> new int[]{3, 7, 10};
            default -> new int[]{7};
        };
    }
}
