package com.astrology.divisional;

import com.astrology.core.Rashi;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * D9 Navamsa calculation tests.
 *
 * D9 rules (each sign has 9 navamsas of 3°20' = 3.333° each):
 *   Fire signs (Aries, Leo, Sagittarius)  → navamsa 1 starts at Aries
 *   Earth signs (Taurus, Virgo, Capricorn) → navamsa 1 starts at Capricorn
 *   Air signs (Gemini, Libra, Aquarius)    → navamsa 1 starts at Libra
 *   Water signs (Cancer, Scorpio, Pisces)  → navamsa 1 starts at Cancer
 *
 * Navamsa index (0-based) = floor(degreesInSign / 3.333)
 * Result = start + index (mod 12)
 */
class DivisionalChartTest {

    @Test
    void testD9Navamsa() {
        // 0° Aries (0.0°): Fire sign, index=0 → Aries+0 = ARIES ✓
        assertEquals(Rashi.ARIES, ChartCalculators.calculateSign(0.0, 9));

        // 3°20' Aries (3.334°): Fire sign, index=1 → Aries+1 = TAURUS ✓
        assertEquals(Rashi.TAURUS, ChartCalculators.calculateSign(3.334, 9));

        // 0° Taurus (30.0°): Earth sign, index=0 → Capricorn+0 = CAPRICORN ✓
        assertEquals(Rashi.CAPRICORN, ChartCalculators.calculateSign(30.0, 9));

        // 10° Taurus (40.0°): Earth sign, index=floor(10/3.333)=3 → Capricorn+3 = ARIES ✓
        assertEquals(Rashi.ARIES, ChartCalculators.calculateSign(40.0, 9));

        // 0° Cancer (90.0°): Water sign, index=0 → Cancer+0 = CANCER ✓
        assertEquals(Rashi.CANCER, ChartCalculators.calculateSign(90.0, 9));

        // 0° Libra (180.0°): Air sign, index=0 → Libra+0 = LIBRA ✓
        assertEquals(Rashi.LIBRA, ChartCalculators.calculateSign(180.0, 9));

        // 0° Sagittarius (240.0°): Fire sign, index=0 → Aries+0 = ARIES ✓
        assertEquals(Rashi.ARIES, ChartCalculators.calculateSign(240.0, 9));
    }
}
