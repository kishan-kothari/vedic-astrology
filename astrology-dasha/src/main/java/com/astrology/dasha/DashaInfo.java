package com.astrology.dasha;

import com.astrology.core.Planet;
import java.time.LocalDate;

public record DashaInfo(
    Planet mahadasha,
    Planet antardasha,
    Planet pratyantardasha,
    LocalDate mdStart, LocalDate mdEnd,
    LocalDate adStart, LocalDate adEnd,
    LocalDate pdStart, LocalDate pdEnd,
    String formatted
) {}
