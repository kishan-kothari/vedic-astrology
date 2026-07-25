package com.astrology.strength;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;
import com.astrology.divisional.DivisionalChartSet;
import java.util.Map;
import java.util.EnumMap;

public class ShadbalaCalculator {

    public Map<Planet, ShadbalaResult> calculateAll(BirthChart chart, DivisionalChartSet chartSet) {
        Map<Planet, ShadbalaResult> results = new EnumMap<>(Planet.class);
        for (Planet p : Planet.values()) {
            if (!p.isShadow()) {
                results.put(p, calculateForPlanet(p, chart, chartSet));
            }
        }
        return results;
    }

    public ShadbalaResult calculateForPlanet(Planet planet, BirthChart chart, DivisionalChartSet chartSet) {
        double sthana = SthanaBala.calculate(planet, chart, chartSet);
        double dig = DigBala.calculate(planet, chart);
        double kala = KalaBala.calculate(planet, chart);
        double chesta = ChestaBala.calculate(planet, chart);
        double naisargika = NaisargikaBala.calculate(planet);
        double drik = DrikBala.calculate(planet, chart);
        
        double total = sthana + dig + kala + chesta + naisargika + drik;
        double rupas = total / 60.0;
        double required = ShadbalaResult.getRequiredRupas(planet);
        
        return new ShadbalaResult(
            planet, sthana, dig, kala, chesta, naisargika, drik,
            total, rupas, required, rupas >= required
        );
    }

    public boolean isSufficientlyStrong(Planet planet, double totalRupas) {
        return totalRupas >= ShadbalaResult.getRequiredRupas(planet);
    }

    public double calculateIshtaBala(Planet planet, BirthChart chart) {
        // ucchaBala approximation for demo
        double ucchaBala = 30.0; // In reality fetch from SthanaBala components
        double chesta = ChestaBala.calculate(planet, chart);
        return Math.sqrt(ucchaBala * chesta);
    }

    public double calculateKashtaBala(Planet planet, BirthChart chart) {
        double ucchaBala = 30.0; // In reality fetch from SthanaBala components
        double chesta = ChestaBala.calculate(planet, chart);
        return Math.sqrt((60.0 - ucchaBala) * (60.0 - chesta));
    }
}
