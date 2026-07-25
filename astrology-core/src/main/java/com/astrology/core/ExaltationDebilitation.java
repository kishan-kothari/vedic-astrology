package com.astrology.core;

public class ExaltationDebilitation {

    public enum DignitaryStatus { EXALTED, MOOLATRIKONA, OWN, GREAT_FRIEND, FRIEND, NEUTRAL, ENEMY, GREAT_ENEMY, DEBILITATED }

    public static double getExaltationDegree(Planet planet) {
        return switch (planet) {
            case SUN -> 10.0;
            case MOON -> 33.0; // 3° Taurus
            case MARS -> 298.0; // 28° Capricorn
            case MERCURY -> 165.0; // 15° Virgo
            case JUPITER -> 95.0; // 5° Cancer
            case VENUS -> 357.0; // 27° Pisces
            case SATURN -> 200.0; // 20° Libra
            case RAHU -> 33.0;
            case KETU -> 213.0;
            default -> -1.0;
        };
    }

    public static double getDebilitationDegree(Planet planet) {
        double ex = getExaltationDegree(planet);
        return ex >= 0 ? AstrologyUtils.normalize360(ex + 180.0) : -1.0;
    }

    public static Rashi getExaltationSign(Planet planet) {
        double deg = getExaltationDegree(planet);
        return deg >= 0 ? Rashi.fromLongitude(deg) : null;
    }

    public static Rashi getDebilitationSign(Planet planet) {
        double deg = getDebilitationDegree(planet);
        return deg >= 0 ? Rashi.fromLongitude(deg) : null;
    }

    public static Rashi getMoolatrikonaSign(Planet planet) {
        return switch (planet) {
            case SUN -> Rashi.LEO;
            case MOON -> Rashi.TAURUS;
            case MARS -> Rashi.ARIES;
            case MERCURY -> Rashi.VIRGO;
            case JUPITER -> Rashi.SAGITTARIUS;
            case VENUS -> Rashi.LIBRA;
            case SATURN -> Rashi.AQUARIUS;
            default -> null;
        };
    }

    public static double getMoolatrikonaStart(Planet planet) {
        return switch (planet) {
            case SUN -> 0.0;
            case MOON -> 3.0;
            case MARS -> 0.0;
            case MERCURY -> 15.0;
            case JUPITER -> 0.0;
            case VENUS -> 0.0;
            case SATURN -> 0.0;
            default -> 0.0;
        };
    }

    public static double getMoolatrikonaEnd(Planet planet) {
        return switch (planet) {
            case SUN -> 20.0;
            case MOON -> 30.0;
            case MARS -> 12.0;
            case MERCURY -> 20.0;
            case JUPITER -> 10.0;
            case VENUS -> 15.0;
            case SATURN -> 20.0;
            default -> 0.0;
        };
    }

    public static double getExaltationDistance(Planet planet, double siderealLongitude) {
        double ex = getExaltationDegree(planet);
        if (ex < 0) return -1.0;
        return AstrologyUtils.angularDistance(ex, siderealLongitude);
    }

    public static DignitaryStatus getDignitaryStatus(Planet planet, double siderealLongitude) {
        Rashi sign = Rashi.fromLongitude(siderealLongitude);
        double degreeInSign = siderealLongitude % 30.0;

        if (getExaltationDegree(planet) >= 0 && Math.abs(getExaltationDegree(planet) - siderealLongitude) < 0.001) { // Exact exaltation is a point, but we handle it as within the sign logic mostly. Wait, the exact degree might not be hit.
            // Let's implement simpler check. Exalted if in exaltation sign?
            // Actually, getDignitaryStatus is asked to return DignitaryStatus.
            // Usually, exaltation is considered if in the sign of exaltation, but specific degree is deep exaltation.
            // I will use sign-based for exaltation/debilitation.
        }
        
        if (getExaltationSign(planet) == sign) return DignitaryStatus.EXALTED;
        if (getDebilitationSign(planet) == sign) return DignitaryStatus.DEBILITATED;

        if (getMoolatrikonaSign(planet) == sign && degreeInSign >= getMoolatrikonaStart(planet) && degreeInSign <= getMoolatrikonaEnd(planet)) {
            return DignitaryStatus.MOOLATRIKONA;
        }

        if (sign.getLord() == planet) return DignitaryStatus.OWN;

        PlanetaryRelationship.Relationship rel = PlanetaryRelationship.getNaturalRelationship(planet, sign.getLord());
        return switch (rel) {
            case GREAT_FRIEND -> DignitaryStatus.GREAT_FRIEND;
            case FRIEND -> DignitaryStatus.FRIEND;
            case NEUTRAL -> DignitaryStatus.NEUTRAL;
            case ENEMY -> DignitaryStatus.ENEMY;
            case GREAT_ENEMY -> DignitaryStatus.GREAT_ENEMY;
        };
    }
}
