package com.astrology.planets;

public class AscendantCalculator {
    public static double calculateFromRAMC(double ramc, double latitude, double obliquity) {
        double ramcRad = Math.toRadians(ramc);
        double latRad = Math.toRadians(latitude);
        double obliqRad = Math.toRadians(obliquity);
        
        double y = -Math.cos(ramcRad);
        double x = Math.sin(obliqRad) * Math.tan(latRad) + Math.cos(obliqRad) * Math.sin(ramcRad);
        
        double ascRad = Math.atan2(y, x);
        double ascDeg = Math.toDegrees(ascRad);
        
        if (ascDeg < 0) {
            ascDeg += 360.0;
        }
        return ascDeg;
    }
    
    public static double calculateObliquity(double jd) {
        double t = (jd - 2451545.0) / 36525.0;
        double eps0 = 23.43929111 - (46.8150 / 3600.0) * t - (0.00059 / 3600.0) * t * t + (0.001813 / 3600.0) * t * t * t;
        return eps0;
    }
    
    public static double calculateRAMC(double jd, double geoLongitude) {
        double d = jd - 2451545.0;
        double t = d / 36525.0;
        double gmst = 280.46061837 + 360.98564736629 * d + 0.000387933 * t * t - t * t * t / 38710000.0;
        
        double ramc = (gmst + geoLongitude) % 360.0;
        if (ramc < 0) ramc += 360.0;
        return ramc;
    }
}
