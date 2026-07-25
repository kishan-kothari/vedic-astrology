package com.astrology.core;

/**
 * Ayanamsha types supported by Swiss Ephemeris.
 */
public enum AyanamshaType {
    LAHIRI(1, "Lahiri"),
    RAMAN(3, "Raman"),
    KRISHNAMURTI(5, "Krishnamurti"),
    FAGAN_BRADLEY(0, "Fagan-Bradley"),
    KP(5, "KP");

    private final int seCode;
    private final String displayName;

    AyanamshaType(int seCode, String displayName) {
        this.seCode = seCode;
        this.displayName = displayName;
    }

    public int getSeCode() {
        return seCode;
    }

    public String getDisplayName() {
        return displayName;
    }
}
