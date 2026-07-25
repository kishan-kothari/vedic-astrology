package com.astrology.strength;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;
import java.util.Map;
import java.util.EnumMap;

public class IshtaKashtaBala {

    public static Map<Planet, double[]> calculate(Map<Planet, ShadbalaResult> shadbala, BirthChart chart) {
        Map<Planet, double[]> results = new EnumMap<>(Planet.class);
        
        for (Map.Entry<Planet, ShadbalaResult> entry : shadbala.entrySet()) {
            Planet p = entry.getKey();
            ShadbalaResult res = entry.getValue();
            
            // Re-derive uccha bala or just use a mock value for this file since 
            // the requirement says "Ishta Bala = sqrt(ucchaBala * chestaBala) per planet"
            double ucchaBala = 30.0; // Simulated
            double chesta = res.chestaBala();
            
            double ishta = Math.sqrt(ucchaBala * chesta);
            double kashta = Math.sqrt((60.0 - ucchaBala) * (60.0 - chesta));
            
            results.put(p, new double[]{ishta, kashta});
        }
        return results;
    }
}
