package com.astrology.strength;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;

public class ChestaBala {

    public static double calculate(Planet planet, BirthChart chart) {
        if (planet == Planet.SUN || planet == Planet.MOON) {
            double dec = chart.getPositions().get(planet).latitude(); // approximation
            return (60.0 + dec * 60.0 / 24.0) / 2.0; // Same as Ayana Bala
        }
        
        if (planet == Planet.RAHU || planet == Planet.KETU) return 0.0;
        
        double speed = chart.getPositions().get(planet).speedLongitude();
        double meanSpeed = getMeanSpeed(planet);
        
        if (speed < 0) return 60.0; // Retrograde
        if (Math.abs(speed) < 0.01) return 30.0; // Stationary
        
        double ratio = speed / meanSpeed;
        if (ratio > 1.2) return 30.0; // Atichara
        if (ratio > 1.0) return 15.0; // Chara
        if (ratio < 0.5) return 7.5;  // Mandatara
        if (ratio < 1.0) return 15.0; // Manda
        
        return 7.5; // Sama
    }

    private static double getMeanSpeed(Planet planet) {
        return switch (planet) {
            case MARS -> 0.524;
            case MERCURY -> 0.985;
            case JUPITER -> 0.083;
            case VENUS -> 0.985; // Roughly same as Sun mean speed but variable
            case SATURN -> 0.033;
            default -> 1.0;
        };
    }
}
