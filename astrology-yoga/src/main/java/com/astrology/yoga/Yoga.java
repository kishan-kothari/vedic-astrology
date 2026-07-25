package com.astrology.yoga;

import com.astrology.core.Planet;
import java.util.List;

public record Yoga(
    String name,
    String sanskritName,
    YogaCategory category,
    String description,
    String effects,
    List<Planet> planets,
    List<Integer> houses,
    double strength,
    boolean isPresent,
    String formationDetails
) {
    public enum YogaCategory {
        PANCHA_MAHAPURUSHA, RAJA_YOGA, DHANA_YOGA, VIPARITA_RAJA_YOGA,
        LUNAR_YOGA, NABHASYA_YOGA, SOLAR_YOGA, SANKHYA_YOGA, CHANDRAYOGA,
        DOSHA_YOGA, SPECIAL_YOGA, GRAHA_YOGA, NAKSATRA_YOGA
    }
}
