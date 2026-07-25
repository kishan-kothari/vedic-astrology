package com.astrology.core;

import java.util.List;

public enum Rashi {
    ARIES(1, "Aries", "Mesh", Element.FIRE, Quality.MOVABLE, Planet.MARS),
    TAURUS(2, "Taurus", "Vrishabha", Element.EARTH, Quality.FIXED, Planet.VENUS),
    GEMINI(3, "Gemini", "Mithuna", Element.AIR, Quality.DUAL, Planet.MERCURY),
    CANCER(4, "Cancer", "Karka", Element.WATER, Quality.MOVABLE, Planet.MOON),
    LEO(5, "Leo", "Simha", Element.FIRE, Quality.FIXED, Planet.SUN),
    VIRGO(6, "Virgo", "Kanya", Element.EARTH, Quality.DUAL, Planet.MERCURY),
    LIBRA(7, "Libra", "Tula", Element.AIR, Quality.MOVABLE, Planet.VENUS),
    SCORPIO(8, "Scorpio", "Vrishchika", Element.WATER, Quality.FIXED, Planet.MARS),
    SAGITTARIUS(9, "Sagittarius", "Dhanu", Element.FIRE, Quality.DUAL, Planet.JUPITER),
    CAPRICORN(10, "Capricorn", "Makara", Element.EARTH, Quality.MOVABLE, Planet.SATURN),
    AQUARIUS(11, "Aquarius", "Kumbha", Element.AIR, Quality.FIXED, Planet.SATURN),
    PISCES(12, "Pisces", "Meena", Element.WATER, Quality.DUAL, Planet.JUPITER);

    public enum Element { FIRE, EARTH, AIR, WATER }
    public enum Quality { MOVABLE, FIXED, DUAL }
    public enum Varna { BRAHMIN, KSHATRIYA, VAISHYA, SHUDRA }
    public enum Vashya { CHATUSHPADA, DWIPADA, JALACHARA, KEETA, VANCHARA }

    private final int number;
    private final String englishName;
    private final String sanskritName;
    private final Element element;
    private final Quality quality;
    private final Planet lord;

    Rashi(int number, String englishName, String sanskritName, Element element, Quality quality, Planet lord) {
        this.number = number;
        this.englishName = englishName;
        this.sanskritName = sanskritName;
        this.element = element;
        this.quality = quality;
        this.lord = lord;
    }

    public int getNumber() { return number; }
    public String getEnglishName() { return englishName; }
    public String getSanskritName() { return sanskritName; }
    public Element getElement() { return element; }
    public Quality getQuality() { return quality; }
    public Planet getLord() { return lord; }

    public static Rashi fromNumber(int n) {
        int index = ((n - 1) % 12 + 12) % 12;
        return values()[index];
    }

    public static Rashi fromLongitude(double lon) {
        double normalized = AstrologyUtils.normalize360(lon);
        int index = (int) (normalized / 30.0);
        return values()[index];
    }

    public Rashi getOpposite() {
        return fromNumber(this.number + 6);
    }

    public boolean isFriendOf(Planet p) {
        return PlanetaryRelationship.getNaturalRelationship(p, this.lord) == PlanetaryRelationship.Relationship.FRIEND
                || PlanetaryRelationship.getNaturalRelationship(p, this.lord) == PlanetaryRelationship.Relationship.GREAT_FRIEND;
    }

    public boolean isEnemyOf(Planet p) {
        return PlanetaryRelationship.getNaturalRelationship(p, this.lord) == PlanetaryRelationship.Relationship.ENEMY
                || PlanetaryRelationship.getNaturalRelationship(p, this.lord) == PlanetaryRelationship.Relationship.GREAT_ENEMY;
    }

    public double exaltationDegree(Planet p) {
        return ExaltationDebilitation.getExaltationSign(p) == this ? ExaltationDebilitation.getExaltationDegree(p) : -1.0;
    }

    public boolean debilitationSign(Planet p) {
        return ExaltationDebilitation.getDebilitationSign(p) == this;
    }

    public Varna getVarna() {
        return switch (this.element) {
            case WATER -> Varna.BRAHMIN;
            case FIRE -> Varna.KSHATRIYA;
            case EARTH -> Varna.VAISHYA;
            case AIR -> Varna.SHUDRA;
        };
    }

    public Vashya getVashya(double longitudeInSign) {
        return switch (this) {
            case ARIES, TAURUS, LEO -> Vashya.CHATUSHPADA;
            case GEMINI, VIRGO, LIBRA, AQUARIUS -> Vashya.DWIPADA;
            case CANCER, PISCES -> Vashya.JALACHARA;
            case SCORPIO -> Vashya.KEETA;
            case SAGITTARIUS -> longitudeInSign < 15.0 ? Vashya.DWIPADA : Vashya.CHATUSHPADA;
            case CAPRICORN -> longitudeInSign < 15.0 ? Vashya.CHATUSHPADA : Vashya.JALACHARA;
        };
    }
}
