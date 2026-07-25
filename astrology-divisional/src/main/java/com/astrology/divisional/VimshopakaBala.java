package com.astrology.divisional;

import com.astrology.core.Planet;
import java.util.Map;
import java.util.EnumMap;

public class VimshopakaBala {
    
    private static final Map<Integer, Double> WEIGHTS = Map.ofEntries(
        Map.entry(1, 3.5),
        Map.entry(2, 1.0),
        Map.entry(3, 1.0),
        Map.entry(4, 0.5),
        Map.entry(7, 0.5),
        Map.entry(9, 3.0),
        Map.entry(10, 0.5),
        Map.entry(12, 0.5),
        Map.entry(16, 2.0),
        Map.entry(20, 0.5),
        Map.entry(24, 0.5),
        Map.entry(27, 0.5),
        Map.entry(30, 1.0),
        Map.entry(40, 0.5),
        Map.entry(45, 0.5),
        Map.entry(60, 4.0)
    );

    // Using Object for exaltation parameter to avoid missing class errors
    public static Map<Planet, Double> calculate(DivisionalChartSet chartSet, Object exaltation) {
        Map<Planet, Double> scores = new EnumMap<>(Planet.class);
        
        for (Planet p : Planet.values()) {
            double totalScore = 0;
            double totalWeight = 0;
            for (Map.Entry<Integer, Double> weightEntry : WEIGHTS.entrySet()) {
                int div = weightEntry.getKey();
                double weight = weightEntry.getValue();
                DivisionalChart chart = chartSet.getChart(div);
                
                if (chart != null && chart.positions().containsKey(p)) {
                    // Simplified: Assuming neutral score (10) for now.
                    // Full implementation would evaluate Rashi vs Planet dignity
                    double positionScore = 10.0; 
                    totalScore += positionScore * weight;
                    totalWeight += weight;
                }
            }
            if (totalWeight > 0) {
                scores.put(p, totalScore / totalWeight); // Scale out of 20
            } else {
                scores.put(p, 0.0);
            }
        }
        
        return scores;
    }
}
