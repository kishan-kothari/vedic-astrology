package com.astrology.transit;

import java.time.LocalDateTime;
import com.astrology.core.Planet;

/**
 * Represents a significant transit event.
 */
public record TransitEvent(
    LocalDateTime eventDate,
    Planet planet,
    String description,
    TransitEventType type,
    double importance
) {
    public enum TransitEventType {
        SIGN_INGRESS,
        RETROGRADE_BEGINS,
        RETROGRADE_ENDS,
        EXACT_ASPECT,
        CONJUNCTION,
        ECLIPSE
    }
}
