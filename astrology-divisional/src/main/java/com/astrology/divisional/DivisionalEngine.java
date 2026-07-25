package com.astrology.divisional;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.planets.BirthChart;
import com.astrology.planets.PlanetPosition;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DivisionalEngine {

    public List<DivisionalChart> calculateAllCharts(BirthChart birthChart) {
        return Arrays.asList(
            calculate(birthChart, 1),
            calculate(birthChart, 2),
            calculate(birthChart, 3),
            calculate(birthChart, 4),
            calculate(birthChart, 7),
            calculate(birthChart, 9),
            calculate(birthChart, 10),
            calculate(birthChart, 12),
            calculate(birthChart, 16),
            calculate(birthChart, 20),
            calculate(birthChart, 24),
            calculate(birthChart, 27),
            calculate(birthChart, 30),
            calculate(birthChart, 40),
            calculate(birthChart, 45),
            calculate(birthChart, 60)
        );
    }

    public DivisionalChart calculate(BirthChart birthChart, int divisor) {
        String name = getChartName(divisor);
        String purpose = getChartPurpose(divisor);
        
        Map<Planet, Rashi> positions = new EnumMap<>(Planet.class);
        for (Planet p : Planet.values()) {
            PlanetPosition pp = birthChart.getPositions().get(p);
            if (pp != null) {
                positions.put(p, calculateDivisionalSign(pp.siderealLongitude(), divisor));
            }
        }
        
        Rashi lagnaRashi = calculateDivisionalSign(birthChart.getLagnaLongitude(), divisor);
        
        return new DivisionalChart(divisor, name, purpose, positions, lagnaRashi);
    }

    private Rashi calculateDivisionalSign(double siderealLongitude, int divisor) {
        return ChartCalculators.calculateSign(siderealLongitude, divisor);
    }

    private String getChartName(int divisor) {
        return switch (divisor) {
            case 1 -> "D1 Rasi";
            case 2 -> "D2 Hora";
            case 3 -> "D3 Drekkana";
            case 4 -> "D4 Chaturthamsa";
            case 7 -> "D7 Saptamsa";
            case 9 -> "D9 Navamsa";
            case 10 -> "D10 Dasamsa";
            case 12 -> "D12 Dwadasamsa";
            case 16 -> "D16 Shodasamsa";
            case 20 -> "D20 Vimshamsa";
            case 24 -> "D24 Siddhamsa";
            case 27 -> "D27 Nakshatramsa";
            case 30 -> "D30 Trimshamsa";
            case 40 -> "D40 Khavedamsa";
            case 45 -> "D45 Akshavedamsa";
            case 60 -> "D60 Shashtiamsa";
            default -> "Unknown D" + divisor;
        };
    }

    private String getChartPurpose(int divisor) {
        return switch (divisor) {
            case 1 -> "Body, general life";
            case 2 -> "Wealth";
            case 3 -> "Siblings, courage";
            case 4 -> "Property, fortune";
            case 7 -> "Children";
            case 9 -> "Soul, marriage, dharma";
            case 10 -> "Career, profession";
            case 12 -> "Parents";
            case 16 -> "Vehicles, pleasures";
            case 20 -> "Spiritual progress";
            case 24 -> "Learning, education";
            case 27 -> "Strengths and weaknesses";
            case 30 -> "Misfortunes, evils";
            case 40 -> "Auspicious/inauspicious effects";
            case 45 -> "General indications";
            case 60 -> "Everything, past life karma";
            default -> "Unknown";
        };
    }
}
