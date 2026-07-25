package com.astrology.strength;

import com.astrology.core.Planet;
import java.util.Map;

/**
 * Result of Shadbala calculation for a single planet.
 * Units are in virupas (unless otherwise noted) and rupas (virupas / 60).
 */
public record ShadbalaResult(
    Planet planet,
    double sthanaBala,
    double digBala,
    double kalaBala,
    double chestaBala,
    double naisargikaBala,
    double drikBala,
    double totalShadbala,
    double totalRupas,
    double requiredRupas,
    boolean isSufficient
) {
    private static final Map<Planet, Double> REQUIRED_RUPAS = Map.of(
        Planet.SUN, 6.5,
        Planet.MOON, 6.0,
        Planet.MARS, 5.0,
        Planet.MERCURY, 7.0,
        Planet.JUPITER, 6.5,
        Planet.VENUS, 5.5,
        Planet.SATURN, 5.0
    );

    public static double getRequiredRupas(Planet planet) {
        return REQUIRED_RUPAS.getOrDefault(planet, 0.0);
    }

    public String toFormattedString() {
        return String.format(
            "Planet: %s | Sthana: %.2f | Dig: %.2f | Kala: %.2f | Chesta: %.2f | Naisargika: %.2f | Drik: %.2f | Total: %.2f Virupas (%.2f Rupas) | Sufficient: %b",
            planet, sthanaBala, digBala, kalaBala, chestaBala, naisargikaBala, drikBala, totalShadbala, totalRupas, isSufficient
        );
    }

    public double getStrengthPercentage() {
        if (requiredRupas == 0) return 100.0;
        return (totalRupas / requiredRupas) * 100.0;
    }
}
