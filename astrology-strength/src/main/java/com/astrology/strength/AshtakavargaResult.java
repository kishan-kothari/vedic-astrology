package com.astrology.strength;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import java.util.Map;
import java.util.EnumMap;
import java.util.Arrays;

public class AshtakavargaResult {
    private final Map<Planet, int[]> bhinnaAshtakavarga;
    private final Map<Planet, int[]> reducedAshtakavarga;
    private final int[] sarvashtakavarga;
    private final Map<Planet, int[][]> prastarashtakavarga;

    public AshtakavargaResult(
        Map<Planet, int[]> bhinnaAshtakavarga,
        Map<Planet, int[]> reducedAshtakavarga,
        int[] sarvashtakavarga,
        Map<Planet, int[][]> prastarashtakavarga
    ) {
        this.bhinnaAshtakavarga = bhinnaAshtakavarga;
        this.reducedAshtakavarga = reducedAshtakavarga;
        this.sarvashtakavarga = sarvashtakavarga;
        this.prastarashtakavarga = prastarashtakavarga;
    }

    public Map<Planet, int[][]> getPrastarashtakavarga() { return prastarashtakavarga; }

    public int getBhinnaBindus(Planet planet, int signIndex) {
        return bhinnaAshtakavarga.get(planet)[signIndex];
    }

    public int getReducedBindus(Planet planet, int signIndex) {
        return reducedAshtakavarga.get(planet)[signIndex];
    }

    public int getSarvashtakavargaBindus(int signIndex) {
        return sarvashtakavarga[signIndex];
    }

    public int getTotalBindus() {
        return Arrays.stream(sarvashtakavarga).sum();
    }

    public int getTransitBindus(Planet planet, Rashi transitRashi) {
        return bhinnaAshtakavarga.get(planet)[transitRashi.getNumber() - 1];
    }

    public String toAsciiTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ashtakavarga Result:\n");
        sb.append("Planet\\Sign | ");
        for (int i = 0; i < 12; i++) sb.append(String.format("%2d ", i + 1));
        sb.append("\n");
        for (Planet p : bhinnaAshtakavarga.keySet()) {
            sb.append(String.format("%-11s | ", p.name()));
            for (int i = 0; i < 12; i++) {
                sb.append(String.format("%2d ", bhinnaAshtakavarga.get(p)[i]));
            }
            sb.append("\n");
        }
        sb.append("Sarva       | ");
        for (int i = 0; i < 12; i++) {
            sb.append(String.format("%2d ", sarvashtakavarga[i]));
        }
        sb.append("\nTotal Bindus: ").append(getTotalBindus()).append("\n");
        return sb.toString();
    }
}
