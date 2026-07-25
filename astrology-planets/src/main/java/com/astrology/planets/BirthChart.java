package com.astrology.planets;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BirthChart {
    private final BirthData birthData;
    private final double julianDayUT;
    private final double julianDayET;
    private final double ayanamsha;
    private final double lagnaLongitude;
    private final Rashi lagnaRashi;
    private final double midheaven;
    private final Map<Planet, PlanetPosition> positions;
    private final double[] houseCusps;
    
    public BirthChart(BirthData birthData, double julianDayUT, double julianDayET, double ayanamsha,
                      double lagnaLongitude, Rashi lagnaRashi, double midheaven,
                      Map<Planet, PlanetPosition> positions, double[] houseCusps) {
        this.birthData = birthData;
        this.julianDayUT = julianDayUT;
        this.julianDayET = julianDayET;
        this.ayanamsha = ayanamsha;
        this.lagnaLongitude = lagnaLongitude;
        this.lagnaRashi = lagnaRashi;
        this.midheaven = midheaven;
        this.positions = positions;
        this.houseCusps = houseCusps;
    }
    
    public PlanetPosition getPlanet(Planet p) {
        return positions.get(p);
    }
    
    public int getHouseOfPlanet(Planet p) {
        PlanetPosition pos = positions.get(p);
        return pos != null ? pos.house() : -1;
    }
    
    public Rashi getSignOfHouse(int houseNumber) {
        if (houseNumber < 1 || houseNumber > 12) return null;
        int signIndex = (lagnaRashi.ordinal() + houseNumber - 1) % 12;
        return Rashi.values()[signIndex];
    }
    
    public Planet getLordOfHouse(int houseNumber) {
        Rashi sign = getSignOfHouse(houseNumber);
        if (sign == null) return null;
        // Basic mapping for lord (Sun=0, Moon=1, Mars=2, Mercury=3, Jupiter=4, Venus=5, Saturn=6)
        return null; // TODO: map Rashi to lord Planet based on com.astrology.core definitions
    }
    
    public boolean arePlanetsConjunct(Planet a, Planet b, double orbDegrees) {
        return getAngularSeparation(a, b) <= orbDegrees;
    }
    
    public boolean arePlanetsInMutualReception(Planet a, Planet b) {
        // Need ruler mapping for this
        return false;
    }
    
    public boolean isPlanetInKendra(Planet p) {
        int h = getHouseOfPlanet(p);
        return h == 1 || h == 4 || h == 7 || h == 10;
    }
    
    public boolean isPlanetInTrikona(Planet p) {
        int h = getHouseOfPlanet(p);
        return h == 1 || h == 5 || h == 9;
    }
    
    public boolean isPlanetInDussthana(Planet p) {
        int h = getHouseOfPlanet(p);
        return h == 6 || h == 8 || h == 12;
    }
    
    public List<Planet> getPlanetsInHouse(int house) {
        List<Planet> inHouse = new ArrayList<>();
        for (Map.Entry<Planet, PlanetPosition> entry : positions.entrySet()) {
            if (entry.getValue().house() == house) {
                inHouse.add(entry.getKey());
            }
        }
        return inHouse;
    }
    
    public double getAngularSeparation(Planet a, Planet b) {
        PlanetPosition posA = getPlanet(a);
        PlanetPosition posB = getPlanet(b);
        if (posA == null || posB == null) return 360.0;
        
        double diff = Math.abs(posA.siderealLongitude() - posB.siderealLongitude());
        if (diff > 180.0) {
            diff = 360.0 - diff;
        }
        return diff;
    }

    public enum Paya { GOLD, SILVER, COPPER, IRON }

    public Paya getPaya() {
        int moonHouse = getHouseOfPlanet(Planet.MOON);
        return switch (moonHouse) {
            case 1, 6, 11 -> Paya.GOLD;
            case 2, 5, 9 -> Paya.SILVER;
            case 3, 7, 10 -> Paya.COPPER;
            case 4, 8, 12 -> Paya.IRON;
            default -> Paya.SILVER;
        };
    }
    
    // getters
    public BirthData getBirthData() { return birthData; }
    public double getJulianDayUT() { return julianDayUT; }
    public double getJulianDayET() { return julianDayET; }
    public double getAyanamsha() { return ayanamsha; }
    public double getLagnaLongitude() { return lagnaLongitude; }
    public Rashi getLagnaRashi() { return lagnaRashi; }
    public double getMidheaven() { return midheaven; }
    public Map<Planet, PlanetPosition> getPositions() { return Collections.unmodifiableMap(positions); }
    public double[] getHouseCusps() { return houseCusps; }
}
