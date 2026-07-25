package com.astrology.api.dto;

import java.util.List;

public record ChartResponse(
    BirthDataRequest birthData,
    String ayanamsha,
    List<PlanetInfo> planets,
    List<HouseInfo> houses,
    AscendantInfo ascendant
) {
    public record PlanetInfo(String name, double degree, String sign, String nakshatra, boolean isRetrograde) {}
    public record HouseInfo(int number, String sign, List<String> occupants) {}
    public record AscendantInfo(String sign, double degree) {}
}
