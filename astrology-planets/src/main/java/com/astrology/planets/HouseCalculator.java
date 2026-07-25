package com.astrology.planets;

import com.astrology.core.HouseSystem;
import com.astrology.core.SwissEphemerisService;
import de.thmac.swisseph.SweConst;
import de.thmac.swisseph.SwissEph;

public class HouseCalculator {
    public static int[] getWholeSignHouses(double lagnaLongitude) {
        int[] houses = new int[13];
        int lagnaSign = (int) (lagnaLongitude / 30.0) + 1; // 1 to 12
        for (int i = 1; i <= 12; i++) {
            int sign = (lagnaSign + i - 1) % 12;
            if (sign == 0) sign = 12;
            houses[i] = sign;
        }
        return houses;
    }
    
    public static int getHouseNumber(double planetLongitude, double lagnaLongitude, HouseSystem system) {
        // Assume Whole Sign as default
        int planetSign = (int) (planetLongitude / 30.0);
        int lagnaSign = (int) (lagnaLongitude / 30.0);
        
        int house = (planetSign - lagnaSign + 12) % 12 + 1;
        return house;
    }
    
    public static double[] getPlacidusHouseCusps(double jdUT, double lat, double lon, double ayanamsha, SwissEphemerisService swe) {
        double[] tropCusps = swe.getHouseCusps(jdUT, lat, lon, 'P'); // 'P' for Placidus
        double[] siderealCusps = new double[13];
        for (int i = 1; i <= 12; i++) {
            siderealCusps[i] = (tropCusps[i] - ayanamsha + 360.0) % 360.0;
        }
        return siderealCusps;
    }
    
    public static double getMidheaven(double jdUT, double lat, double lon, double ayanamsha, SwissEphemerisService swe) {
        double[] tropCusps = swe.getHouseCusps(jdUT, lat, lon, 'P');
        return (tropCusps[10] - ayanamsha + 360.0) % 360.0; // 10th house cusp is MC
    }
    
    public static double getNadir(double mcLongitude) {
        return (mcLongitude + 180.0) % 360.0;
    }
}
