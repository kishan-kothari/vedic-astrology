package com.astrology.divisional;

import com.astrology.core.Rashi;

public class ChartCalculators {

    public static Rashi calculateSign(double longitude, int divisor) {
        longitude = longitude % 360.0;
        if (longitude < 0) {
            longitude += 360.0;
        }

        Rashi currentSign = Rashi.values()[(int) (longitude / 30.0)];
        double degreesInSign = longitude % 30.0;

        return switch (divisor) {
            case 1 -> currentSign;
            case 2 -> calculateD2(currentSign, degreesInSign);
            case 3 -> calculateD3(currentSign, degreesInSign);
            case 4 -> calculateD4(currentSign, degreesInSign);
            case 7 -> calculateD7(currentSign, degreesInSign);
            case 9 -> calculateD9(currentSign, degreesInSign);
            case 10 -> calculateD10(currentSign, degreesInSign);
            case 12 -> calculateD12(currentSign, degreesInSign);
            case 16 -> calculateD16(currentSign, degreesInSign);
            case 20 -> calculateD20(currentSign, degreesInSign);
            case 24 -> calculateD24(currentSign, degreesInSign);
            case 27 -> calculateD27(currentSign, degreesInSign);
            case 30 -> calculateD30(currentSign, degreesInSign);
            case 40 -> calculateD40(currentSign, degreesInSign);
            case 45 -> calculateD45(currentSign, degreesInSign);
            case 60 -> calculateD60(currentSign, degreesInSign);
            default -> currentSign;
        };
    }

    private static Rashi getSignFrom(Rashi base, int stepsForward) {
        return Rashi.values()[(base.ordinal() + stepsForward) % 12];
    }

    private static boolean isOdd(Rashi rashi) {
        return rashi.ordinal() % 2 == 0;
    }

    private static boolean isMovable(Rashi rashi) {
        return rashi.ordinal() % 3 == 0;
    }

    private static boolean isFixed(Rashi rashi) {
        return rashi.ordinal() % 3 == 1;
    }

    private static boolean isDual(Rashi rashi) {
        return rashi.ordinal() % 3 == 2;
    }

    private static boolean isFire(Rashi rashi) {
        return rashi.ordinal() % 4 == 0;
    }

    private static boolean isEarth(Rashi rashi) {
        return rashi.ordinal() % 4 == 1;
    }

    private static boolean isAir(Rashi rashi) {
        return rashi.ordinal() % 4 == 2;
    }

    private static boolean isWater(Rashi rashi) {
        return rashi.ordinal() % 4 == 3;
    }

    private static Rashi calculateD2(Rashi sign, double degrees) {
        int part = (int) (degrees / 15.0);
        if (isOdd(sign)) {
            return part == 0 ? Rashi.LEO : Rashi.CANCER;
        } else {
            return part == 0 ? Rashi.CANCER : Rashi.LEO;
        }
    }

    private static Rashi calculateD3(Rashi sign, double degrees) {
        int part = (int) (degrees / 10.0);
        return getSignFrom(sign, part * 4);
    }

    private static Rashi calculateD4(Rashi sign, double degrees) {
        int part = (int) (degrees / 7.5);
        return getSignFrom(sign, part * 3);
    }

    private static Rashi calculateD7(Rashi sign, double degrees) {
        int part = (int) (degrees / (30.0 / 7.0));
        if (isOdd(sign)) {
            return getSignFrom(sign, part);
        } else {
            return getSignFrom(sign, part + 6);
        }
    }

    private static Rashi calculateD9(Rashi sign, double degrees) {
        int part = (int) (degrees / (30.0 / 9.0));
        Rashi start;
        if (isFire(sign)) start = Rashi.ARIES;
        else if (isEarth(sign)) start = Rashi.CAPRICORN;
        else if (isAir(sign)) start = Rashi.LIBRA;
        else start = Rashi.CANCER;
        
        return getSignFrom(start, part);
    }

    private static Rashi calculateD10(Rashi sign, double degrees) {
        int part = (int) (degrees / 3.0);
        if (isOdd(sign)) {
            return getSignFrom(sign, part);
        } else {
            return getSignFrom(sign, part + 8);
        }
    }

    private static Rashi calculateD12(Rashi sign, double degrees) {
        int part = (int) (degrees / 2.5);
        return getSignFrom(sign, part);
    }

    private static Rashi calculateD16(Rashi sign, double degrees) {
        int part = (int) (degrees / (30.0 / 16.0));
        Rashi start;
        if (isMovable(sign)) start = Rashi.ARIES;
        else if (isFixed(sign)) start = Rashi.LEO;
        else start = Rashi.SAGITTARIUS;
        
        return getSignFrom(start, part);
    }

    private static Rashi calculateD20(Rashi sign, double degrees) {
        int part = (int) (degrees / 1.5);
        Rashi start;
        if (isMovable(sign)) start = Rashi.ARIES;
        else if (isFixed(sign)) start = Rashi.SAGITTARIUS;
        else start = Rashi.LEO;
        
        return getSignFrom(start, part);
    }

    private static Rashi calculateD24(Rashi sign, double degrees) {
        int part = (int) (degrees / 1.25);
        Rashi start = isOdd(sign) ? Rashi.LEO : Rashi.CANCER;
        return getSignFrom(start, part);
    }

    private static Rashi calculateD27(Rashi sign, double degrees) {
        int part = (int) (degrees / (30.0 / 27.0));
        Rashi start;
        if (isFire(sign)) start = Rashi.ARIES;
        else if (isEarth(sign)) start = Rashi.CANCER;
        else if (isAir(sign)) start = Rashi.LIBRA;
        else start = Rashi.CAPRICORN;
        
        return getSignFrom(start, part);
    }

    private static Rashi calculateD30(Rashi sign, double degrees) {
        if (isOdd(sign)) {
            if (degrees < 5) return Rashi.ARIES;
            if (degrees < 10) return Rashi.AQUARIUS;
            if (degrees < 18) return Rashi.SAGITTARIUS;
            if (degrees < 25) return Rashi.GEMINI;
            return Rashi.TAURUS;
        } else {
            if (degrees < 5) return Rashi.TAURUS;
            if (degrees < 12) return Rashi.VIRGO;
            if (degrees < 20) return Rashi.PISCES;
            if (degrees < 25) return Rashi.CAPRICORN;
            return Rashi.SCORPIO;
        }
    }

    private static Rashi calculateD40(Rashi sign, double degrees) {
        int part = (int) (degrees / 0.75);
        Rashi start;
        if (isMovable(sign)) start = Rashi.ARIES;
        else if (isFixed(sign)) start = Rashi.LEO;
        else start = Rashi.SAGITTARIUS;
        
        return getSignFrom(start, part);
    }

    private static Rashi calculateD45(Rashi sign, double degrees) {
        int part = (int) (degrees / (30.0 / 45.0));
        Rashi start;
        if (isMovable(sign)) start = Rashi.ARIES;
        else if (isFixed(sign)) start = Rashi.LEO;
        else start = Rashi.SAGITTARIUS;
        
        return getSignFrom(start, part);
    }

    private static Rashi calculateD60(Rashi sign, double degrees) {
        int part = (int) (degrees / 0.5);
        Rashi start = isOdd(sign) ? Rashi.ARIES : Rashi.LIBRA;
        return getSignFrom(start, part);
    }
}
