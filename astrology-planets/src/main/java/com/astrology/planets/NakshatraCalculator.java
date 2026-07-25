package com.astrology.planets;

import com.astrology.core.Planet;
import com.astrology.core.Nakshatra;

public class NakshatraCalculator {
    private static final double NAKSHATRA_SPAN = 360.0 / 27.0; // 13.333333333333334
    private static final double PADA_SPAN = NAKSHATRA_SPAN / 4.0;
    
    public static Nakshatra fromLongitude(double siderealLon) {
        int index = (int) (siderealLon / NAKSHATRA_SPAN);
        if (index >= 27) index = 26; // safety bounds
        return Nakshatra.values()[index];
    }
    
    public static int getPada(double siderealLon) {
        double elapsed = getElapsedDegreesInNakshatra(siderealLon);
        return (int) (elapsed / PADA_SPAN) + 1;
    }
    
    public static double getElapsedDegreesInNakshatra(double siderealLon) {
        return siderealLon % NAKSHATRA_SPAN;
    }
    
    public static double getRemainingDegreesInNakshatra(double siderealLon) {
        return NAKSHATRA_SPAN - getElapsedDegreesInNakshatra(siderealLon);
    }
    
    public static double getRemainingFraction(double siderealLon) {
        return getRemainingDegreesInNakshatra(siderealLon) / NAKSHATRA_SPAN;
    }
    
    public static double getNakshatraStartDegree(Nakshatra n) {
        return n.ordinal() * NAKSHATRA_SPAN;
    }
    
    public static double getNakshatraEndDegree(Nakshatra n) {
        return (n.ordinal() + 1) * NAKSHATRA_SPAN;
    }
    
    public static Planet getDashaLord(double moonSiderealLon) {
        Nakshatra n = fromLongitude(moonSiderealLon);
        // Vimshottari Dasha sequence: Ketu, Venus, Sun, Moon, Mars, Rahu, Jupiter, Saturn, Mercury
        // Ashwini (0) -> Ketu.
        int seq = n.ordinal() % 9;
        switch (seq) {
            case 0: return Planet.valueOf("KETU"); // assuming KETU is in enum
            case 1: return Planet.VENUS;
            case 2: return Planet.SUN;
            case 3: return Planet.MOON;
            case 4: return Planet.MARS;
            case 5: return Planet.valueOf("MEAN_NODE"); // Rahu
            case 6: return Planet.JUPITER;
            case 7: return Planet.SATURN;
            case 8: return Planet.MERCURY;
            default: return null;
        }
    }
    
    public static double getDashaBalance(double moonSiderealLon) {
        Planet lord = getDashaLord(moonSiderealLon);
        int years = getDashaYears(lord);
        return years * getRemainingFraction(moonSiderealLon);
    }
    
    private static int getDashaYears(Planet planet) {
        if (planet == Planet.VENUS) return 20;
        if (planet == Planet.SUN) return 6;
        if (planet == Planet.MOON) return 10;
        if (planet == Planet.MARS) return 7;
        if (planet == Planet.JUPITER) return 16;
        if (planet == Planet.SATURN) return 19;
        if (planet == Planet.MERCURY) return 17;
        String name = planet.name();
        if (name.equals("KETU")) return 7;
        if (name.equals("MEAN_NODE") || name.equals("TRUE_NODE")) return 18; // Rahu
        return 0;
    }
}
