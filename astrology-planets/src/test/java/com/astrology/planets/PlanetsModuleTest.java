package com.astrology.planets;

import com.astrology.core.AyanamshaType;
import com.astrology.core.EphemerisConfig;
import com.astrology.core.HouseSystem;
import com.astrology.core.Planet;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlanetsModuleTest {
    @Test
    public void testGandhiChart() {
        // Oct 2, 1869, 07:11 AM IST, Porbandar (21.6417°N, 69.6083°E)
        BirthData bd = BirthData.builder()
            .name("Mahatma Gandhi")
            .localDateTime(LocalDateTime.of(1869, 10, 2, 7, 11))
            .timezone(ZoneId.of("Asia/Kolkata"))
            .latitude(21.6417)
            .longitude(69.6083)
            .ayanamshaType(AyanamshaType.LAHIRI)
            .houseSystem(HouseSystem.WHOLE_SIGN)
            .build();
            
        EphemerisConfig config = EphemerisConfig.defaults();
        PlanetaryCalculator calculator = new PlanetaryCalculator(config);
        
        // This will just ensure basic flow works without crashing in our stubs
        BirthChart chart = calculator.calculateBirthChart(bd);
        
        assertNotNull(chart);
        assertNotNull(chart.getPlanet(Planet.SUN));
    }
}
