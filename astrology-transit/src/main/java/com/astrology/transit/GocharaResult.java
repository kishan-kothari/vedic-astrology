package com.astrology.transit;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;

/**
 * Represents the result of a Gochara (transit from natal Moon).
 */
public record GocharaResult(
    Planet planet,
    Rashi natalMoonSign,
    Rashi transitSign,
    int houseFromMoon,
    String result,
    Beneficence beneficence,
    int strengthPercent
) {
    public enum Beneficence { VERY_GOOD, GOOD, NEUTRAL, BAD, VERY_BAD }
    public enum SadeSatiPhase { FIRST_PEAK, PEAK, LAST_PEAK, NOT_IN_SADESATI }
}
