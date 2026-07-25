package com.astrology.planets;

import com.astrology.core.SwissEphemerisService;

public class UpagrahaCalculator {
    public static double calculateGulika(BirthData bd, SwissEphemerisService swe) {
        // Gulika: 8th part of Saturn's portion of the day
        // Simplified stub calculation
        return 0.0;
    }
    
    public static double calculateMandi(BirthData bd, SwissEphemerisService swe) {
        // Mandi = Gulika in day births, similar calculation for night
        // Simplified stub calculation
        return 0.0;
    }
    
    public static double calculateDhuma(double sunLongitude) {
        // Dhuma = Sun + 133°20'
        return (sunLongitude + 133.33333333333334) % 360.0;
    }
    
    public static double calculateVyatipata(double dhumaLongitude) {
        // Vyatipata = 360 - Dhuma
        return (360.0 - dhumaLongitude) % 360.0;
    }
    
    public static double calculateParivesha(double vyatipatLongitude) {
        // Parivesha = Vyatipata + 180
        return (vyatipatLongitude + 180.0) % 360.0;
    }
    
    public static double calculateIndrachapa(double pariveshaLongitude) {
        // Indrachapa = 360 - Parivesha
        return (360.0 - pariveshaLongitude) % 360.0;
    }
    
    public static double calculateUpaketu(double sunLongitude) {
        // Upaketu = Sun + 30
        // Or sometimes it's Indrachapa + 16°40' which equals Sun + 30.
        // Let's use Sun + 30 as per specification.
        return (sunLongitude + 30.0) % 360.0;
    }
}
