package com.astrology.yoga;

import com.astrology.planets.BirthChart;
import com.astrology.core.Planet;
import java.util.List;

public interface YogaRule {
    String getYogaName();
    Yoga.YogaCategory getCategory();
    Yoga check(BirthChart chart);
    
    default boolean isPlanetInOwnOrExaltedSign(Planet planet, BirthChart chart) {
        // Mock implementation
        return true; 
    }
    default boolean isPlanetInKendra(Planet planet, BirthChart chart) {
        return true;
    }
    default boolean isPlanetInTrikona(Planet planet, BirthChart chart) {
        return true;
    }
    default boolean arePlanetsConjunct(Planet a, Planet b, BirthChart chart, double orb) {
        return true;
    }
    default boolean isPlanetAspecting(Planet aspect, Planet aspected, BirthChart chart) {
        return true;
    }
}
