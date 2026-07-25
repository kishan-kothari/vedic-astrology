package com.astrology.core;

import java.time.LocalDateTime;

public class JulianDate {

    public static double toJulianDay(int year, int month, int day, double hour) {
        if (month <= 2) {
            year -= 1;
            month += 12;
        }
        double a = Math.floor(year / 100.0);
        double b = 2 - a + Math.floor(a / 4.0);
        return Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) + day + hour / 24.0 + b - 1524.5;
    }

    public static double toJulianDay(LocalDateTime utcDateTime) {
        double hour = utcDateTime.getHour() + utcDateTime.getMinute() / 60.0 + utcDateTime.getSecond() / 3600.0 + utcDateTime.getNano() / 3.6e12;
        return toJulianDay(utcDateTime.getYear(), utcDateTime.getMonthValue(), utcDateTime.getDayOfMonth(), hour);
    }

    public static LocalDateTime fromJulianDay(double jd) {
        double q = jd + 0.5;
        long z = (long) Math.floor(q);
        double f = q - z;
        double a;
        if (z < 2299161) {
            a = z;
        } else {
            long alpha = (long) Math.floor((z - 1867216.25) / 36524.25);
            a = z + 1 + alpha - (long) Math.floor(alpha / 4.0);
        }
        double b = a + 1524;
        long c = (long) Math.floor((b - 122.1) / 365.25);
        long d = (long) Math.floor(365.25 * c);
        long e = (long) Math.floor((b - d) / 30.6001);

        int day = (int) (b - d - Math.floor(30.6001 * e));
        int month = (int) (e < 14 ? e - 1 : e - 13);
        int year = (int) (month > 2 ? c - 4716 : c - 4715);

        double hoursDouble = f * 24.0;
        int hour = (int) hoursDouble;
        double minutesDouble = (hoursDouble - hour) * 60.0;
        int minute = (int) minutesDouble;
        double secondsDouble = (minutesDouble - minute) * 60.0;
        int second = (int) secondsDouble;
        int nano = (int) Math.round((secondsDouble - second) * 1e9);
        if (nano == 1000000000) {
            nano = 0;
            second++;
        }
        // Simplified carry-over handling, assuming java.time handles standard normalization if needed.
        return LocalDateTime.of(year, month, day, hour, minute, second, nano);
    }

    public static double getDeltaT(double jd) {
        // Simplified polynomial approximation, typically provided by Swiss Ephemeris.
        // For accurate use, swe_deltat should be used.
        // Here we provide a dummy fallback 0.0 or a basic approx.
        // A complete real deltaT calculation is complex, return 0 for now unless using Swisseph.
        return 0.0; 
    }

    public static double greenwichSiderealTime(double jd) {
        double d = jd - 2451545.0;
        double t = d / 36525.0;
        double gmst = 280.46061837 + 360.98564736629 * d + 0.000387933 * t * t - t * t * t / 38710000.0;
        return AstrologyUtils.normalize360(gmst);
    }

    public static double localSiderealTime(double jd, double longitude) {
        return AstrologyUtils.normalize360(greenwichSiderealTime(jd) + longitude);
    }
}
