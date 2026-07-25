package com.astrology.transit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import com.astrology.planets.BirthChart;
import com.astrology.core.Planet;
import com.astrology.planets.PlanetPosition;
import com.astrology.core.Rashi;

class TransitEngineTest {

    @Test
    void testSadeSatiDetection() {
        GocharaEngine gocharaEngine = new GocharaEngine();

        Rashi natalMoonSign = Rashi.TAURUS;

        // Saturn in Aries = 12th from Taurus = Sade Sati first peak
        Map<Planet, PlanetPosition> transitPos1 = new HashMap<>();
        PlanetPosition mockSaturn1 = mock(PlanetPosition.class);
        when(mockSaturn1.rashi()).thenReturn(Rashi.ARIES);
        transitPos1.put(Planet.SATURN, mockSaturn1);

        assertTrue(gocharaEngine.isSadeSati(natalMoonSign, transitPos1));
        assertEquals(GocharaResult.SadeSatiPhase.FIRST_PEAK,
                     gocharaEngine.getSadeSatiPhase(natalMoonSign, Rashi.ARIES));

        // Saturn in Taurus = 1st from Taurus = Sade Sati peak
        Map<Planet, PlanetPosition> transitPos2 = new HashMap<>();
        PlanetPosition mockSaturn2 = mock(PlanetPosition.class);
        when(mockSaturn2.rashi()).thenReturn(Rashi.TAURUS);
        transitPos2.put(Planet.SATURN, mockSaturn2);

        assertTrue(gocharaEngine.isSadeSati(natalMoonSign, transitPos2));
        assertEquals(GocharaResult.SadeSatiPhase.PEAK,
                     gocharaEngine.getSadeSatiPhase(natalMoonSign, Rashi.TAURUS));

        // Saturn in Gemini = 2nd from Taurus = Sade Sati last peak
        Map<Planet, PlanetPosition> transitPos3 = new HashMap<>();
        PlanetPosition mockSaturn3 = mock(PlanetPosition.class);
        when(mockSaturn3.rashi()).thenReturn(Rashi.GEMINI);
        transitPos3.put(Planet.SATURN, mockSaturn3);

        assertTrue(gocharaEngine.isSadeSati(natalMoonSign, transitPos3));
        assertEquals(GocharaResult.SadeSatiPhase.LAST_PEAK,
                     gocharaEngine.getSadeSatiPhase(natalMoonSign, Rashi.GEMINI));

        // Saturn in Cancer = 3rd from Taurus = NOT in Sade Sati
        Map<Planet, PlanetPosition> transitPos4 = new HashMap<>();
        PlanetPosition mockSaturn4 = mock(PlanetPosition.class);
        when(mockSaturn4.rashi()).thenReturn(Rashi.CANCER);
        transitPos4.put(Planet.SATURN, mockSaturn4);

        assertFalse(gocharaEngine.isSadeSati(natalMoonSign, transitPos4));
        assertEquals(GocharaResult.SadeSatiPhase.NOT_IN_SADESATI,
                     gocharaEngine.getSadeSatiPhase(natalMoonSign, Rashi.CANCER));
    }

    @Test
    void testGocharaResultForSun() {
        GocharaEngine gocharaEngine = new GocharaEngine();

        Rashi natalMoonSign = Rashi.ARIES;
        Rashi sunTransitSign = Rashi.AQUARIUS; // 11th from Aries

        GocharaResult result = gocharaEngine.calculateGochara(Planet.SUN, natalMoonSign, sunTransitSign);

        assertEquals(11, result.houseFromMoon());
        assertEquals("Gains, success", result.result());
        assertEquals(GocharaResult.Beneficence.GOOD, result.beneficence());
    }

    @Test
    void testTransitHouse() {
        // Mock BirthChart — TransitChart calls getLagnaRashi() now
        BirthChart natalChart = mock(BirthChart.class);
        when(natalChart.getLagnaRashi()).thenReturn(Rashi.LEO);
        when(natalChart.getPositions()).thenReturn(Collections.emptyMap());

        Map<Planet, PlanetPosition> transitPositions = new HashMap<>();
        PlanetPosition jupiterPos = mock(PlanetPosition.class);
        when(jupiterPos.rashi()).thenReturn(Rashi.LIBRA);
        transitPositions.put(Planet.JUPITER, jupiterPos);

        TransitChart chart = new TransitChart(natalChart, transitPositions, LocalDateTime.now(),
            Collections.emptyList(), Collections.emptyList());

        // LEO=5, LIBRA=7. house = (7-5) + 1 = 3
        int house = chart.getTransitHouse(Planet.JUPITER);
        assertEquals(3, house);
    }
}
