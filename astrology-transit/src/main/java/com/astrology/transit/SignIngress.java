package com.astrology.transit;

import java.time.LocalDateTime;
import com.astrology.core.Planet;
import com.astrology.core.Rashi;

/**
 * Represents a planet changing signs (Rashi).
 */
public record SignIngress(
    Planet planet,
    Rashi fromSign,
    Rashi toSign,
    LocalDateTime entryDate
) {}
