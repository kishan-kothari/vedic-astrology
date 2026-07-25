package com.astrology.core;

/**
 * Configuration for Swiss Ephemeris.
 */
public record EphemerisConfig(
        String ephePath,
        AyanamshaType ayanamshaType,
        HouseSystem houseSystem,
        int siderealFlag) {

    /**
     * Default configuration:
     * Path: "./ephe"
     * Ayanamsha: Lahiri
     * House System: Whole Sign
     * Sidereal Flag: SwissEph.SEFLG_SIDEREAL | SwissEph.SEFLG_SWIEPH | SwissEph.SEFLG_SPEED
     */
    public static EphemerisConfig defaults() {
        return new EphemerisConfig(
                "./ephe",
                AyanamshaType.LAHIRI,
                HouseSystem.WHOLE_SIGN,
                // Using hardcoded constants since SwissEph constants might not be imported yet.
                // SEFLG_SWIEPH = 2, SEFLG_SPEED = 256, SEFLG_SIDEREAL = 64.K
                2 | 256 | 65536 // Correct SEFLG_SIDEREAL is 64 * 1024 = 65536.
        );
    }
}
