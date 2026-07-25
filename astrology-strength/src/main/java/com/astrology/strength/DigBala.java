package com.astrology.strength;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;

public class DigBala {

    public static double calculate(Planet planet, BirthChart chart) {
        if (planet == Planet.RAHU || planet == Planet.KETU) return 0.0;
        
        double digbalaPoint = getDigbalaPoint(planet, chart);
        double planetDegree = chart.getPositions().get(planet).siderealLongitude();
        
        double distance = Math.abs(planetDegree - digbalaPoint);
        if (distance > 180.0) {
            distance = 360.0 - distance;
        }
        
        return 60.0 - (distance / 3.0);
    }

    public static double getDigbalaPoint(Planet planet, BirthChart chart) {
        // Return longitude of the cusp that represents max Digbala
        // 1st house cusp for Jup/Mer, 10th for Sun/Mar, 7th for Sat, 4th for Moon/Ven
        return switch (planet) {
            case JUPITER, MERCURY -> chart.getHouseCusps()[0];
            case SUN, MARS -> chart.getHouseCusps()[9];
            case SATURN -> chart.getHouseCusps()[6];
            case MOON, VENUS -> chart.getHouseCusps()[3];
            default -> 0.0;
        };
    }
}
