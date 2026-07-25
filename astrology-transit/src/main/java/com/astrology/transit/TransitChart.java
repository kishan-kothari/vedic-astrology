package com.astrology.transit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.astrology.planets.BirthChart;
import com.astrology.core.Planet;
import com.astrology.planets.PlanetPosition;
import com.astrology.core.Rashi;

/**
 * Represents a transit chart for a specific date and time, 
 * compared against a natal chart.
 */
public class TransitChart {
    private final BirthChart natalChart;
    private final Map<Planet, PlanetPosition> transitPositions;
    private final LocalDateTime transitDateTime;
    private final List<TransitAspect> aspects;
    private final List<TransitAspect> mutualAspects;
    private final GocharaEngine gocharaEngine;

    public TransitChart(
            BirthChart natalChart,
            Map<Planet, PlanetPosition> transitPositions,
            LocalDateTime transitDateTime,
            List<TransitAspect> aspects,
            List<TransitAspect> mutualAspects) {
        this.natalChart = natalChart;
        this.transitPositions = Map.copyOf(transitPositions);
        this.transitDateTime = transitDateTime;
        this.aspects = List.copyOf(aspects);
        this.mutualAspects = List.copyOf(mutualAspects);
        this.gocharaEngine = new GocharaEngine();
    }

    public BirthChart getNatalChart() {
        return natalChart;
    }

    public Map<Planet, PlanetPosition> getTransitPositions() {
        return transitPositions;
    }

    public LocalDateTime getTransitDateTime() {
        return transitDateTime;
    }

    public List<TransitAspect> getAspectsToNatal() {
        return aspects;
    }

    public List<TransitAspect> getMutualTransitAspects() {
        return mutualAspects;
    }

    public int getTransitHouse(Planet planet) {
        PlanetPosition transitPos = transitPositions.get(planet);
        if (transitPos == null || natalChart == null) {
            return -1;
        }
        
        Rashi ascendantRashi = natalChart.getLagnaRashi();
        Rashi transitRashi = transitPos.rashi();
        
        int house = (transitRashi.ordinal() - ascendantRashi.ordinal()) + 1;
        if (house <= 0) {
            house += 12;
        }
        return house;
    }

    public GocharaResult getGocharaFromMoon(Planet planet) {
        if (natalChart == null || !transitPositions.containsKey(planet)) {
            return null;
        }
        
        PlanetPosition natalMoon = natalChart.getPositions().get(Planet.MOON);
        if (natalMoon == null) {
            return null;
        }
        
        Rashi natalMoonSign = natalMoon.rashi();
        Rashi transitSign = transitPositions.get(planet).rashi();
        
        return gocharaEngine.calculateGochara(planet, natalMoonSign, transitSign);
    }
}
