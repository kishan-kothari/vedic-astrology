package com.astrology.planets;

import com.astrology.core.Planet;
import com.astrology.core.SwissEphemerisService;
import java.util.ArrayList;
import java.util.List;

public class RetrogradeDetector {
    public static boolean isRetrograde(double speedLongitude) {
        return speedLongitude < 0.0;
    }
    
    public static boolean isStationary(double speedLongitude) {
        double threshold = 0.0003; // ~1 arcsecond per day
        return Math.abs(speedLongitude) < threshold;
    }
    
    public static List<double[]> findRetrogradePeriods(
        Planet planet, double startJD, double endJD, SwissEphemerisService swe) {
        
        List<double[]> periods = new ArrayList<>();
        double currentJD = startJD;
        double step = 1.0; // 1 day step
        
        boolean wasRetrograde = false;
        double retroStart = -1;
        
        // This is a stub implementation as we do not have the direct bindings to query swe repeatedly here
        while (currentJD <= endJD) {
            // double speed = swe.getPlanetSpeed(planet, currentJD);
            // boolean isRetro = isRetrograde(speed);
            // if (isRetro && !wasRetrograde) { retroStart = currentJD; }
            // else if (!isRetro && wasRetrograde) { periods.add(new double[]{retroStart, currentJD}); }
            // wasRetrograde = isRetro;
            currentJD += step;
        }
        
        return periods;
    }
}
