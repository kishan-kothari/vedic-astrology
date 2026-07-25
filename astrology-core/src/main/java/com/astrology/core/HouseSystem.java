package com.astrology.core;

/**
 * House systems supported.
 */
public enum HouseSystem {
    WHOLE_SIGN('W', "Whole Sign"),
    PLACIDUS('P', "Placidus"),
    EQUAL('E', "Equal"),
    KOCH('K', "Koch");

    private final char code;
    private final String displayName;

    HouseSystem(char code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public char getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
