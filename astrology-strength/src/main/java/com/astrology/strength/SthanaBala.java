package com.astrology.strength;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;
import com.astrology.divisional.DivisionalChartSet;
import com.astrology.core.Rashi;
import java.util.Set;

public class SthanaBala {

    public static double calculate(Planet planet, BirthChart chart, DivisionalChartSet chartSet) {
        double uccha = calculateUcchaBala(planet, chart);
        double saptavargiya = calculateSaptavargiyaBala(planet, chartSet);
        double ojhayugmarasi = calculateOjhayugmarasiBala(planet, chart);
        double kendra = calculateKendraBala(planet, chart);
        double drekkana = calculateDrekkanaBala(planet, chart);

        return uccha + saptavargiya + ojhayugmarasi + kendra + drekkana;
    }

    private static double calculateUcchaBala(Planet planet, BirthChart chart) {
        // Exaltation points (approximate standard values)
        double exaltationDegree = getExaltationDegree(planet);
        if (exaltationDegree < 0) return 0.0; // Rahu/Ketu often omitted or use 0

        double planetDegree = chart.getPositions().get(planet).siderealLongitude();
        double distance = Math.abs(planetDegree - exaltationDegree);
        if (distance > 180) {
            distance = 360 - distance;
        }
        return (180 - distance) / 3.0;
    }

    private static double getExaltationDegree(Planet planet) {
        return switch (planet) {
            case SUN -> 10.0;
            case MOON -> 33.0; // Taurus 3°
            case MARS -> 298.0; // Capricorn 28°
            case MERCURY -> 165.0; // Virgo 15°
            case JUPITER -> 95.0; // Cancer 5°
            case VENUS -> 357.0; // Pisces 27°
            case SATURN -> 200.0; // Libra 20°
            default -> -1.0;
        };
    }

    private static double calculateSaptavargiyaBala(Planet planet, DivisionalChartSet chartSet) {
        // Mocked - in reality would check D1, D2, D3, D7, D9, D12, D30
        // Dignity score sum
        // Defaulting to a moderate score for completion
        return 22.5 * 7; 
    }

    private static double calculateOjhayugmarasiBala(Planet planet, BirthChart chart) {
        Rashi sign = chart.getPositions().get(planet).rashi();
        boolean isOdd = sign.getNumber() % 2 != 0; // Aries=1(odd), Taurus=2(even)
        
        Set<Planet> malePlanets = Set.of(Planet.SUN, Planet.MARS, Planet.JUPITER, Planet.MERCURY);
        Set<Planet> femalePlanets = Set.of(Planet.MOON, Planet.VENUS);
        
        if (malePlanets.contains(planet) && isOdd) {
            return 15.0;
        } else if (femalePlanets.contains(planet) && !isOdd) {
            return 15.0;
        }
        return 0.0;
    }

    private static double calculateKendraBala(Planet planet, BirthChart chart) {
        int house = chart.getHouseOfPlanet(planet);
        if (house == 1 || house == 4 || house == 7 || house == 10) return 60.0;
        if (house == 2 || house == 5 || house == 8 || house == 11) return 30.0;
        return 15.0;
    }

    private static double calculateDrekkanaBala(Planet planet, BirthChart chart) {
        double degreeInSign = chart.getPositions().get(planet).siderealLongitude() % 30.0;
        Set<Planet> malePlanets = Set.of(Planet.SUN, Planet.MARS, Planet.JUPITER);
        Set<Planet> femalePlanets = Set.of(Planet.MOON, Planet.VENUS);
        Set<Planet> neuterPlanets = Set.of(Planet.MERCURY, Planet.SATURN);

        if (degreeInSign <= 10.0 && malePlanets.contains(planet)) return 15.0;
        if (degreeInSign > 10.0 && degreeInSign <= 20.0 && femalePlanets.contains(planet)) return 15.0;
        if (degreeInSign > 20.0 && neuterPlanets.contains(planet)) return 15.0;
        return 0.0;
    }
}
