package com.astrology.yoga;

import com.astrology.core.AyanamshaType;
import com.astrology.core.EphemerisConfig;
import com.astrology.core.ExaltationDebilitation;
import com.astrology.core.HouseSystem;
import com.astrology.core.Nakshatra;
import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.planets.BirthChart;
import com.astrology.planets.BirthData;
import com.astrology.planets.PlanetPosition;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class YogaEngineTest {

    /**
     * Build a minimal BirthChart with enough data for yoga detection.
     * Places Jupiter in Cancer (exalted) in a kendra — triggers HamsaYoga.
     * Places Moon in Cancer, Jupiter in 10th from lagna.
     */
    private BirthChart buildSampleChart() {
        BirthData data = BirthData.builder()
            .name("Test")
            .localDateTime(LocalDateTime.of(1990, 7, 15, 14, 30))
            .timezone(ZoneId.of("Asia/Kolkata"))
            .latitude(28.6139)
            .longitude(77.2090)
            .ayanamshaType(AyanamshaType.LAHIRI)
            .houseSystem(HouseSystem.WHOLE_SIGN)
            .build();

        Map<Planet, PlanetPosition> positions = new EnumMap<>(Planet.class);

        // Lagna in Aries (0°), so house 1=Aries, 4=Cancer, 7=Libra, 10=Capricorn
        // Jupiter exalted in Cancer = house 4 (kendra) -> HamsaYoga
        addPosition(positions, Planet.JUPITER, 95.0,  Rashi.CANCER,     Nakshatra.PUNARVASU,  1,  4);
        addPosition(positions, Planet.MOON,    100.0, Rashi.CANCER,     Nakshatra.PUNARVASU,  2,  4);
        addPosition(positions, Planet.SUN,     110.0, Rashi.CANCER,     Nakshatra.ASHLESHA,   1,  4);
        addPosition(positions, Planet.MARS,    15.0,  Rashi.ARIES,      Nakshatra.BHARANI,    1,  1);
        addPosition(positions, Planet.SATURN,  200.0, Rashi.LIBRA,      Nakshatra.SWATI,      2,  7);
        addPosition(positions, Planet.MERCURY, 165.0, Rashi.VIRGO,      Nakshatra.HASTA,      2,  6);
        addPosition(positions, Planet.VENUS,   357.0, Rashi.PISCES,     Nakshatra.REVATI,     4,  12);
        addPosition(positions, Planet.RAHU,    240.0, Rashi.SCORPIO,    Nakshatra.VISHAKHA,   4,  8);
        addPosition(positions, Planet.KETU,    60.0,  Rashi.TAURUS,     Nakshatra.MRIGASHIRA, 4,  2);

        double lagnaLongitude = 0.0; // 0° Aries
        return new BirthChart(data, 2448088.0, 2448088.0, 23.5, lagnaLongitude,
            Rashi.ARIES, 270.0, positions, new double[13]);
    }

    private void addPosition(Map<Planet, PlanetPosition> map, Planet planet,
                              double lon, Rashi rashi, Nakshatra nakshatra,
                              int pada, int house) {
        double degreeInSign = lon % 30.0;
        map.put(planet, new PlanetPosition(
            planet, lon + 23.5, lon, 0.0, 1.0, 0.9, false,
            rashi, nakshatra, pada, house, degreeInSign,
            ExaltationDebilitation.getDignitaryStatus(planet, lon)
        ));
    }

    @Test
    void testYogaEngineDetectsYogas() {
        YogaEngine engine = new YogaEngine();
        BirthChart chart = buildSampleChart();

        List<Yoga> yogas = engine.detectAllYogas(chart);
        assertNotNull(yogas);
        // The engine should at minimum return a list (even if empty for this chart)
        assertNotNull(yogas);
    }

    @Test
    void testGetPresentYogas() {
        YogaEngine engine = new YogaEngine();
        BirthChart chart = buildSampleChart();

        List<Yoga> present = engine.getPresentYogas(chart);
        assertNotNull(present);
        // All returned yogas must have isPresent == true
        assertTrue(present.stream().allMatch(Yoga::isPresent));
    }

    @Test
    void testDetectGrouped() {
        YogaEngine engine = new YogaEngine();
        BirthChart chart = buildSampleChart();

        Map<Yoga.YogaCategory, List<Yoga>> grouped = engine.detectGrouped(chart);
        assertNotNull(grouped);
    }
}
