package com.astrology.core;

public final class AstrologyUtils {

    private AstrologyUtils() {}

    public static double normalize360(double degrees) {
        double d = degrees % 360.0;
        if (d < 0) d += 360.0;
        return d;
    }

    public static double normalize180(double degrees) {
        double d = degrees % 360.0;
        if (d < -180.0) d += 360.0;
        else if (d >= 180.0) d -= 360.0;
        return d;
    }

    public static String toDMS(double degrees) {
        double d = Math.abs(degrees);
        int deg = (int) d;
        double rem = (d - deg) * 60;
        int min = (int) rem;
        double sec = (rem - min) * 60;
        String sign = degrees < 0 ? "-" : "";
        return String.format("%s%d°%02d'%02d\"", sign, deg, min, (int)Math.round(sec));
    }

    public static String toZodiac(double siderealDegrees) {
        double norm = normalize360(siderealDegrees);
        int signIndex = (int) (norm / 30.0);
        double rem = norm % 30.0;
        int deg = (int) rem;
        int min = (int) ((rem - deg) * 60);
        String[] signs = {"Ari", "Tau", "Gem", "Can", "Leo", "Vir", "Lib", "Sco", "Sag", "Cap", "Aqu", "Pis"};
        return String.format("%02d°%02d'%s", deg, min, signs[signIndex]);
    }

    public static double angularDistance(double lon1, double lon2) {
        double diff = Math.abs(lon1 - lon2) % 360.0;
        return diff > 180.0 ? 360.0 - diff : diff;
    }

    public static boolean isWithinOrb(double lon1, double lon2, double orb) {
        return angularDistance(lon1, lon2) <= orb;
    }

    public static int getWholeSignHouse(double planetLongitude, double lagnaLongitude) {
        int planetSign = (int) (normalize360(planetLongitude) / 30.0);
        int lagnaSign = (int) (normalize360(lagnaLongitude) / 30.0);
        return ((planetSign - lagnaSign + 12) % 12) + 1;
    }

    public static String julianDayToString(double jd) {
        return JulianDate.fromJulianDay(jd).toString();
    }

    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    public static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }
}
