package com.astrology.transit;

import com.astrology.core.Planet;

/**
 * Represents an aspect between a transiting planet and a natal planet (or another transiting planet).
 */
public record TransitAspect(
    Planet transitPlanet,
    Planet natalPlanet,
    double transitLongitude,
    double natalLongitude,
    double orb,
    AspectType aspectType,
    boolean isApplying,
    boolean isSeparating,
    double exactnessPercent
) {
    public enum AspectType {
        CONJUNCTION(0, 8),
        OPPOSITION(180, 8),
        TRINE(120, 7),
        SQUARE(90, 6),
        SEXTILE(60, 5);
        
        public final int degrees;
        public final int maxOrb;
        
        AspectType(int degrees, int maxOrb) {
            this.degrees = degrees;
            this.maxOrb = maxOrb;
        }
    }
    
    public enum VedicAspect {
        FULL(100), THREE_QUARTER(75), HALF(50), QUARTER(25);
        
        public final int strength;
        
        VedicAspect(int strength) {
            this.strength = strength;
        }
    }
}
