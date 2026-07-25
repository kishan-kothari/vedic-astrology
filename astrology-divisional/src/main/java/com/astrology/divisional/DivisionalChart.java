package com.astrology.divisional;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a specific divisional chart (Varga).
 */
public record DivisionalChart(
    int divisor,
    String name,
    String purpose,
    Map<Planet, Rashi> positions,
    Rashi lagnaRashi
) {
    public Map<Planet, Integer> getHousePositions() {
        Map<Planet, Integer> housePositions = new EnumMap<>(Planet.class);
        for (Map.Entry<Planet, Rashi> entry : positions.entrySet()) {
            housePositions.put(entry.getKey(), calculateHouse(lagnaRashi, entry.getValue()));
        }
        return housePositions;
    }

    private int calculateHouse(Rashi lagna, Rashi planetSign) {
        int lagnaIndex = lagna.ordinal();
        int planetSignIndex = planetSign.ordinal();
        int house = planetSignIndex - lagnaIndex + 1;
        if (house <= 0) {
            house += 12;
        }
        return house;
    }

    public String toAsciiTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Divisional Chart: %s (D%d) - %s\n", name, divisor, purpose));
        sb.append("Lagna: ").append(lagnaRashi.name()).append("\n");
        Map<Planet, Integer> housePos = getHousePositions();
        
        sb.append("Planet Positions:\n");
        for (Planet p : Planet.values()) {
            if (positions.containsKey(p)) {
                sb.append(String.format("%-10s : %-12s (House %d)\n", p.name(), positions.get(p).name(), housePos.get(p)));
            }
        }
        
        return sb.toString();
    }
}
