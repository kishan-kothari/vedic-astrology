package com.astrology.core;

public enum Nakshatra {
    ASHWINI(1, "Ashwini", Planet.KETU, 0.0, Yoni.ASHWA, Gana.DEVA, Nadi.AADI),
    BHARANI(2, "Bharani", Planet.VENUS, 13.333333333333334, Yoni.GAJA, Gana.MANUSHYA, Nadi.MADHYA),
    KRITTIKA(3, "Krittika", Planet.SUN, 26.666666666666668, Yoni.MESHA, Gana.RAKSHASA, Nadi.ANTYA),
    ROHINI(4, "Rohini", Planet.MOON, 40.0, Yoni.SARPA, Gana.MANUSHYA, Nadi.ANTYA),
    MRIGASHIRA(5, "Mrigashira", Planet.MARS, 53.333333333333336, Yoni.SARPA, Gana.DEVA, Nadi.MADHYA),
    ARDRA(6, "Ardra", Planet.RAHU, 66.66666666666667, Yoni.SHVAN, Gana.MANUSHYA, Nadi.AADI),
    PUNARVASU(7, "Punarvasu", Planet.JUPITER, 80.0, Yoni.MARJAR, Gana.DEVA, Nadi.AADI),
    PUSHYA(8, "Pushya", Planet.SATURN, 93.33333333333333, Yoni.MESHA, Gana.DEVA, Nadi.MADHYA),
    ASHLESHA(9, "Ashlesha", Planet.MERCURY, 106.66666666666667, Yoni.MARJAR, Gana.RAKSHASA, Nadi.ANTYA),
    MAGHA(10, "Magha", Planet.KETU, 120.0, Yoni.MOOSHAK, Gana.RAKSHASA, Nadi.ANTYA),
    PURVA_PHALGUNI(11, "Purva Phalguni", Planet.VENUS, 133.33333333333334, Yoni.MOOSHAK, Gana.MANUSHYA, Nadi.MADHYA),
    UTTARA_PHALGUNI(12, "Uttara Phalguni", Planet.SUN, 146.66666666666666, Yoni.GAU, Gana.MANUSHYA, Nadi.AADI),
    HASTA(13, "Hasta", Planet.MOON, 160.0, Yoni.MAHISH, Gana.DEVA, Nadi.AADI),
    CHITRA(14, "Chitra", Planet.MARS, 173.33333333333334, Yoni.VYAGHRA, Gana.RAKSHASA, Nadi.MADHYA),
    SWATI(15, "Swati", Planet.RAHU, 186.66666666666666, Yoni.MAHISH, Gana.DEVA, Nadi.ANTYA),
    VISHAKHA(16, "Vishakha", Planet.JUPITER, 200.0, Yoni.VYAGHRA, Gana.RAKSHASA, Nadi.ANTYA),
    ANURADHA(17, "Anuradha", Planet.SATURN, 213.33333333333334, Yoni.MRIG, Gana.DEVA, Nadi.MADHYA),
    JYESHTHA(18, "Jyeshtha", Planet.MERCURY, 226.66666666666666, Yoni.MRIG, Gana.RAKSHASA, Nadi.AADI),
    MOOLA(19, "Moola", Planet.KETU, 240.0, Yoni.SHVAN, Gana.RAKSHASA, Nadi.AADI),
    PURVA_ASHADHA(20, "Purva Ashadha", Planet.VENUS, 253.33333333333334, Yoni.VANAR, Gana.MANUSHYA, Nadi.MADHYA),
    UTTARA_ASHADHA(21, "Uttara Ashadha", Planet.SUN, 266.6666666666667, Yoni.NAKUL, Gana.MANUSHYA, Nadi.ANTYA),
    SHRAVANA(22, "Shravana", Planet.MOON, 280.0, Yoni.VANAR, Gana.DEVA, Nadi.ANTYA),
    DHANISHTHA(23, "Dhanishtha", Planet.MARS, 293.3333333333333, Yoni.SIMHA, Gana.RAKSHASA, Nadi.MADHYA),
    SHATABHISHA(24, "Shatabhisha", Planet.RAHU, 306.6666666666667, Yoni.ASHWA, Gana.RAKSHASA, Nadi.AADI),
    PURVA_BHADRAPADA(25, "Purva Bhadrapada", Planet.JUPITER, 320.0, Yoni.SIMHA, Gana.MANUSHYA, Nadi.AADI),
    UTTARA_BHADRAPADA(26, "Uttara Bhadrapada", Planet.SATURN, 333.3333333333333, Yoni.GAU, Gana.MANUSHYA, Nadi.MADHYA),
    REVATI(27, "Revati", Planet.MERCURY, 346.6666666666667, Yoni.GAJA, Gana.DEVA, Nadi.ANTYA);

    public static final double NAKSHATRA_SPAN = 13.333333333333334;
    public static final double PADA_SPAN = 3.3333333333333335;

    public enum Yoni { ASHWA, GAJA, MESHA, SARPA, SHVAN, MARJAR, MESHK, MOOSHAK, VYAGHRA, MAHISH, MRIG, VANAR, NAKUL, SIMHA, GAU }
    public enum Gana { DEVA, MANUSHYA, RAKSHASA }
    public enum Nadi { AADI, MADHYA, ANTYA }

    private final int number;
    private final String name;
    private final Planet lord;
    private final double startDegree;
    private final Yoni yoni;
    private final Gana gana;
    private final Nadi nadi;

    Nakshatra(int number, String name, Planet lord, double startDegree, Yoni yoni, Gana gana, Nadi nadi) {
        this.number = number;
        this.name = name;
        this.lord = lord;
        this.startDegree = startDegree;
        this.yoni = yoni;
        this.gana = gana;
        this.nadi = nadi;
    }

    public int getNumber() { return number; }
    public String getName() { return name; }
    public Planet getLord() { return lord; }
    public double getStartDegree() { return startDegree; }
    public Yoni getYoni() { return yoni; }
    public Gana getGana() { return gana; }
    public Nadi getNadi() { return nadi; }

    public int getPada(double longitude) {
        double normalized = AstrologyUtils.normalize360(longitude);
        double offset = normalized - this.startDegree;
        if (offset < 0) offset += 360.0;
        return (int) (offset / PADA_SPAN) + 1;
    }

    public static Nakshatra fromLongitude(double siderealLon) {
        double normalized = AstrologyUtils.normalize360(siderealLon);
        int index = (int) (normalized / NAKSHATRA_SPAN);
        if (index >= 27) index = 26; // safety bounds
        return values()[index];
    }
}
