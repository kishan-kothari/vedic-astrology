package com.astrology.report;

import com.astrology.core.Planet;

import java.util.ArrayList;
import java.util.List;

public class KPSystem {

    public record KPEntry(
            String name,
            double longitude,
            Planet signLord,
            Planet starLord,
            Planet subLord
    ) {}

    private static final Planet[] VIM_SEQ = {
            Planet.KETU, Planet.VENUS, Planet.SUN, Planet.MOON, Planet.MARS,
            Planet.RAHU, Planet.JUPITER, Planet.SATURN, Planet.MERCURY
    };

    private static final double[] VIM_YEARS = {
            7.0, 20.0, 6.0, 10.0, 7.0, 18.0, 16.0, 19.0, 17.0
    };

    private static final double TOTAL_YEARS = 120.0;
    private static final double NAKSHATRA_SPAN = 360.0 / 27.0; // 13.333333 degrees

    public static Planet getStarLord(double longitude) {
        int nakIndex = (int) Math.floor(longitude / NAKSHATRA_SPAN);
        // Ashwini (0) -> Ketu.
        // Sequence repeats every 9 nakshatras.
        return VIM_SEQ[nakIndex % 9];
    }

    public static Planet getSignLord(double longitude) {
        int signIndex = (int) Math.floor(longitude / 30.0);
        return switch (signIndex) {
            case 0, 7 -> Planet.MARS;      // Aries, Scorpio
            case 1, 6 -> Planet.VENUS;     // Taurus, Libra
            case 2, 5 -> Planet.MERCURY;   // Gemini, Virgo
            case 3 -> Planet.MOON;         // Cancer
            case 4 -> Planet.SUN;          // Leo
            case 8, 11 -> Planet.JUPITER;  // Sagittarius, Pisces
            case 9, 10 -> Planet.SATURN;   // Capricorn, Aquarius
            default -> Planet.SUN;
        };
    }

    public static Planet getSubLord(double longitude) {
        int nakIndex = (int) Math.floor(longitude / NAKSHATRA_SPAN);
        double elapsedInNakshatra = longitude % NAKSHATRA_SPAN;

        int starLordIndex = nakIndex % 9;
        
        int currentSubLordIndex = starLordIndex;
        double currentElapsed = 0.0;

        for (int i = 0; i < 9; i++) {
            double subSpan = (VIM_YEARS[currentSubLordIndex] / TOTAL_YEARS) * NAKSHATRA_SPAN;
            currentElapsed += subSpan;
            
            if (elapsedInNakshatra <= currentElapsed) {
                return VIM_SEQ[currentSubLordIndex];
            }
            
            currentSubLordIndex = (currentSubLordIndex + 1) % 9;
        }

        return VIM_SEQ[starLordIndex]; // Fallback, should not be reached
    }

    public static String formatLongitude(double longitude) {
        int sign = (int) (longitude / 30) + 1;
        double degrees = longitude % 30;
        int d = (int) degrees;
        int m = (int) ((degrees - d) * 60);
        int s = (int) Math.round((((degrees - d) * 60) - m) * 60);
        return String.format("%d %02d° %02d' %02d\"", sign, d, m, s);
    }
}
