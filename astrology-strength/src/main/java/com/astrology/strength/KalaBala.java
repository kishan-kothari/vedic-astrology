package com.astrology.strength;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;
import java.time.LocalDate;
import java.util.Set;

public class KalaBala {

    public static double calculate(Planet planet, BirthChart chart) {
        if (planet == Planet.RAHU || planet == Planet.KETU) return 0.0;
        
        double natonnata = calculateNatonnataBala(planet, chart);
        double paksha = calculatePakshaBala(planet, chart);
        double tribhaga = calculateTribhagaBala(planet, chart);
        double kalapati = calculateKalapati(planet, chart);
        double ayana = calculateAyanaBala(planet, chart);
        double yuddha = calculateYuddhaBala(planet, chart);
        
        return natonnata + paksha + tribhaga + kalapati + ayana + yuddha;
    }

    private static double calculateNatonnataBala(Planet planet, BirthChart chart) {
        boolean isDayBirth = chart.getBirthData().localDateTime().getHour() >= 6 && chart.getBirthData().localDateTime().getHour() <= 18;
        if (planet == Planet.MERCURY) return 30.0;
        
        Set<Planet> dayStrong = Set.of(Planet.SUN, Planet.JUPITER, Planet.VENUS);
        Set<Planet> nightStrong = Set.of(Planet.MOON, Planet.MARS, Planet.SATURN);
        
        if (isDayBirth) {
            return dayStrong.contains(planet) ? 60.0 : 30.0;
        } else {
            return nightStrong.contains(planet) ? 60.0 : 30.0;
        }
    }

    private static double calculatePakshaBala(Planet planet, BirthChart chart) {
        double moonLong = chart.getPositions().get(Planet.MOON).siderealLongitude();
        double sunLong = chart.getPositions().get(Planet.SUN).siderealLongitude();
        double moonAngle = (moonLong - sunLong + 360) % 360;
        
        Set<Planet> benefics = Set.of(Planet.MOON, Planet.JUPITER, Planet.VENUS, Planet.MERCURY);
        
        if (moonAngle <= 180) { // Shukla Paksha
            double moonBala = moonAngle / 3.0;
            return benefics.contains(planet) ? moonBala : (60.0 - moonBala);
        } else { // Krishna Paksha
            double moonBala = (360 - moonAngle) / 3.0;
            return benefics.contains(planet) ? moonBala : (60.0 - moonBala);
        }
    }

    private static double calculateTribhagaBala(Planet planet, BirthChart chart) {
        if (planet == Planet.JUPITER) return 60.0;
        // Mocking tribhaga
        return 0.0; 
    }

    private static double calculateKalapati(Planet planet, BirthChart chart) {
        double score = 0.0;
        // Mock Varsha, Masa, Vara, Hora Pati
        if (planet == getWeekdayLord(chart.getBirthData().localDateTime().getDayOfWeek().getValue())) {
            score += 45.0;
        }
        return score;
    }

    private static double calculateAyanaBala(Planet planet, BirthChart chart) {
        double dec = chart.getPositions().get(planet).latitude(); // approximation
        return (60.0 + dec * 60.0 / 24.0) / 2.0;
    }
    
    private static double calculateYuddhaBala(Planet planet, BirthChart chart) {
        return 0.0; // Planetary war logic
    }

    private static Planet getWeekdayLord(int dayOfWeek) {
        // Java DayOfWeek: 1=Monday, 7=Sunday
        return switch (dayOfWeek) {
            case 7 -> Planet.SUN;
            case 1 -> Planet.MOON;
            case 2 -> Planet.MARS;
            case 3 -> Planet.MERCURY;
            case 4 -> Planet.JUPITER;
            case 5 -> Planet.VENUS;
            case 6 -> Planet.SATURN;
            default -> Planet.SUN;
        };
    }
}
