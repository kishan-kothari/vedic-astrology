package com.astrology.core;

import de.thmac.swisseph.SwissEph;

public class SwissEphemerisService implements AutoCloseable {
    private final ThreadLocal<SwissEph> sweLocal;
    private final EphemerisConfig config;

    public SwissEphemerisService(EphemerisConfig config) {
        this.config = config;
        sweLocal = ThreadLocal.withInitial(() -> {
            SwissEph swe = new SwissEph(config.ephePath());
            swe.swe_set_sid_mode(config.ayanamshaType().getSeCode(), 0, 0);
            return swe;
        });
    }

    public double[] calcPlanetPosition(double julianDayET, int planetCode, int flags) {
        SwissEph swe = sweLocal.get();
        double[] xx = new double[6];
        StringBuffer serr = new StringBuffer();
        int ret = swe.swe_calc(julianDayET, planetCode, flags, xx, serr);
        if (ret < 0) {
            throw new AstrologyException(AstrologyException.EPHEMERIS_ERROR, "Error calculating planet " + planetCode + ": " + serr);
        }
        return xx;
    }

    public double getTropicalLongitude(double julianDayET, int planetCode) {
        // SEFLG_SWIEPH = 2, SEFLG_SPEED = 256
        return calcPlanetPosition(julianDayET, planetCode, 2 | 256)[0];
    }

    public double getSiderealLongitude(double julianDayET, int planetCode) {
        return calcPlanetPosition(julianDayET, planetCode, config.siderealFlag())[0];
    }

    public double getAyanamsha(double julianDayUT) {
        return sweLocal.get().swe_get_ayanamsa_ut(julianDayUT);
    }

    public double[] getHouseCusps(double julianDayUT, double latitude, double longitude, char houseSystem) {
        SwissEph swe = sweLocal.get();
        double[] cusps = new double[13];
        double[] ascmc = new double[10];
        swe.swe_houses(julianDayUT, 0, latitude, longitude, houseSystem, cusps, ascmc);
        double[] result = new double[13];
        result[0] = ascmc[0]; // Ascendant
        System.arraycopy(cusps, 1, result, 1, 12);
        return result;
    }

    public double getPlanetSpeed(double julianDayET, int planetCode) {
        return calcPlanetPosition(julianDayET, planetCode, config.siderealFlag())[3];
    }

    @Override
    public void close() {
        sweLocal.get().swe_close();
        sweLocal.remove();
    }
}
