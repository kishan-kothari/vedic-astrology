package com.astrology.strength;

import com.astrology.core.Planet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StrengthTest {

    @Test
    public void testNaisargikaBala() {
        assertEquals(60.0, NaisargikaBala.calculate(Planet.SUN), 0.01);
        assertEquals(51.43, NaisargikaBala.calculate(Planet.MOON), 0.01);
        assertEquals(8.57, NaisargikaBala.calculate(Planet.SATURN), 0.01);
    }

    @Test
    public void testTrikonaShodhana() {
        AshtakavargaEngine engine = new AshtakavargaEngine();
        
        // Mocking a bhinna array where Aries=4, Leo=5, Sagittarius=6
        int[] input = new int[12];
        input[0] = 4; // Aries
        input[4] = 5; // Leo
        input[8] = 6; // Sagittarius
        
        int[] reduced = engine.trikonaShodhana(input);
        
        assertEquals(0, reduced[0]);
        assertEquals(1, reduced[4]);
        assertEquals(2, reduced[8]);
        
        // Case with a zero
        input[0] = 0;
        input[4] = 5;
        input[8] = 6;
        reduced = engine.trikonaShodhana(input);
        
        assertEquals(0, reduced[0]);
        assertEquals(5, reduced[4]); // No reduction if one is 0
        assertEquals(6, reduced[8]);
    }
}
