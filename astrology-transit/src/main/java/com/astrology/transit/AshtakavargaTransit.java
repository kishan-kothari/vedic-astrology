package com.astrology.transit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.strength.AshtakavargaResult;

public class AshtakavargaTransit {

    public int getTransitBindus(Planet planet, Rashi transitSign, AshtakavargaResult aav) {
        return aav.getTransitBindus(planet, transitSign);
    }

    public Map<Rashi, Integer> getTransitStrengthMap(Planet planet, AshtakavargaResult bav) {
        Map<Rashi, Integer> map = new LinkedHashMap<>();
        for (Rashi sign : Rashi.values()) {
            map.put(sign, getTransitBindus(planet, sign, bav));
        }
        return map;
    }

    public List<Rashi> getBestTransitSigns(Planet planet, AshtakavargaResult bav, int minBindus) {
        List<Rashi> bestSigns = new ArrayList<>();
        for (Rashi sign : Rashi.values()) {
            if (getTransitBindus(planet, sign, bav) >= minBindus) {
                bestSigns.add(sign);
            }
        }
        return bestSigns;
    }

    public boolean isGoodTransit(Planet planet, Rashi transitSign, AshtakavargaResult bav) {
        return getTransitBindus(planet, transitSign, bav) >= 4;
    }
}
