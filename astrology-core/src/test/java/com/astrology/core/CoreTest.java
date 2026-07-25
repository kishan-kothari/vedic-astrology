package com.astrology.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoreTest {

    @Test
    public void testJulianDay() {
        double jd = JulianDate.toJulianDay(1990, 7, 15, 14.5);
        assertEquals(2448088.104, jd, 1.0); // Rough check against known value
    }

    @Test
    public void testNormalize360() {
        assertEquals(350.0, AstrologyUtils.normalize360(-10.0), 0.001);
    }

    @Test
    public void testNakshatra() {
        assertEquals(Nakshatra.ASHWINI, Nakshatra.fromLongitude(0.0));
        assertEquals(Nakshatra.BHARANI, Nakshatra.fromLongitude(13.334));
    }

    @Test
    public void testRashi() {
        assertEquals(Rashi.TAURUS, Rashi.fromLongitude(35.5));
    }

    @Test
    public void testExaltationDebilitation() {
        assertEquals(ExaltationDebilitation.DignitaryStatus.EXALTED, 
                ExaltationDebilitation.getDignitaryStatus(Planet.SUN, 10.0));
        assertEquals(ExaltationDebilitation.DignitaryStatus.DEBILITATED, 
                ExaltationDebilitation.getDignitaryStatus(Planet.SUN, 190.0));
    }
}
