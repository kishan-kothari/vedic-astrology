package com.astrology.core;

public enum Planet {
    SUN(0, "Sun", "☉", true, false),
    MOON(1, "Moon", "☽", true, false),
    MERCURY(2, "Mercury", "☿", false, false),
    VENUS(3, "Venus", "♀", false, false),
    MARS(4, "Mars", "♂", false, false),
    JUPITER(5, "Jupiter", "♃", false, false),
    SATURN(6, "Saturn", "♄", false, false),
    RAHU(10, "Rahu", "☊", false, true),
    KETU(-1, "Ketu", "☋", false, true),
    GULIKA(-2, "Gulika", "G", false, true),
    MANDI(-3, "Mandi", "M", false, true);

    private final int seCode;
    private final String name;
    private final String symbol;
    private final boolean luminary;
    private final boolean shadow;

    Planet(int seCode, String name, String symbol, boolean luminary, boolean shadow) {
        this.seCode = seCode;
        this.name = name;
        this.symbol = symbol;
        this.luminary = luminary;
        this.shadow = shadow;
    }

    public int getSeCode() { return seCode; }
    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public boolean isLuminary() { return luminary; }
    public boolean isShadow() { return shadow; }

    public boolean isNaturalBenefic() {
        return this == MOON || this == MERCURY || this == JUPITER || this == VENUS;
    }

    public boolean isNaturalMalefic() {
        return this == SUN || this == MARS || this == SATURN || this == RAHU || this == KETU;
    }

    public double getNaisargikaBala() {
        return switch (this) {
            case SUN -> 60.0;
            case MOON -> 51.43;
            case VENUS -> 42.86;
            case JUPITER -> 34.29;
            case MERCURY -> 25.71;
            case MARS -> 17.14;
            case SATURN -> 8.57;
            default -> 0.0;
        };
    }

    public int getVimshottariYears() {
        return switch (this) {
            case KETU -> 7;
            case VENUS -> 20;
            case SUN -> 6;
            case MOON -> 10;
            case MARS -> 7;
            case RAHU -> 18;
            case JUPITER -> 16;
            case SATURN -> 19;
            case MERCURY -> 17;
            default -> 0;
        };
    }
}
