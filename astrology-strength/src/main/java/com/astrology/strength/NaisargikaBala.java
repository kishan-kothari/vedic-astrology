package com.astrology.strength;

import com.astrology.core.Planet;
import java.util.Map;

public class NaisargikaBala {

    public static final Map<Planet, Double> BALA_VALUES = Map.of(
        Planet.SUN, 60.0,
        Planet.MOON, 51.43,
        Planet.VENUS, 42.86,
        Planet.JUPITER, 34.29,
        Planet.MERCURY, 25.71,
        Planet.MARS, 17.14,
        Planet.SATURN, 8.57
    );

    public static double calculate(Planet planet) {
        return BALA_VALUES.getOrDefault(planet, 0.0);
    }
}
