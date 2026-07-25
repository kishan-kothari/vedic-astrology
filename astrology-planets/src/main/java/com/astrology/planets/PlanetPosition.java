package com.astrology.planets;

import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.core.Nakshatra;
import com.astrology.core.ExaltationDebilitation;

public record PlanetPosition(
    Planet planet,
    double tropicalLongitude,   // ecliptic longitude before ayanamsha
    double siderealLongitude,   // tropical - ayanamsha
    double latitude,            // celestial latitude
    double distance,            // in AU
    double speedLongitude,      // degrees/day, negative = retrograde
    boolean retrograde,
    Rashi rashi,                // zodiac sign (1-12)
    Nakshatra nakshatra,        // nakshatra (1-27)
    int pada,                   // nakshatra pada (1-4)
    int house,                  // house number (1-12, whole sign from lagna)
    double degreeInSign,        // 0-30 degrees within the sign
    ExaltationDebilitation.DignitaryStatus dignitaryStatus
) {
    public static PlanetPosition of(Planet planet, double[] sweResult, double ayanamsha, double lagnaLongitude) {
        double tropicalLongitude = sweResult[0];
        double siderealLongitude = (tropicalLongitude - ayanamsha) % 360.0;
        if (siderealLongitude < 0) {
            siderealLongitude += 360.0;
        }
        
        double latitude = sweResult[1];
        double distance = sweResult[2];
        double speedLongitude = sweResult[3];
        boolean retrograde = speedLongitude < 0;
        
        Rashi rashi = Rashi.values()[(int) (siderealLongitude / 30.0)];
        Nakshatra nakshatra = NakshatraCalculator.fromLongitude(siderealLongitude);
        int pada = NakshatraCalculator.getPada(siderealLongitude);
        int house = HouseCalculator.getHouseNumber(siderealLongitude, lagnaLongitude, null);
        double degreeInSign = siderealLongitude % 30.0;
        
        // DignitaryStatus is calculated separately and injected or evaluated based on Rashi and Degree
        ExaltationDebilitation.DignitaryStatus dignitaryStatus = ExaltationDebilitation.DignitaryStatus.NEUTRAL; 
        
        return new PlanetPosition(
            planet, tropicalLongitude, siderealLongitude, latitude, distance, 
            speedLongitude, retrograde, rashi, nakshatra, pada, house, degreeInSign, dignitaryStatus
        );
    }
    
    public int[] toDMS() {
        double deg = Math.floor(degreeInSign);
        double minutesFloat = (degreeInSign - deg) * 60;
        double min = Math.floor(minutesFloat);
        double sec = Math.round((minutesFloat - min) * 60);
        
        if (sec == 60) {
            sec = 0;
            min++;
        }
        if (min == 60) {
            min = 0;
            deg++;
        }
        return new int[]{(int)deg, (int)min, (int)sec};
    }
    
    public String toZodiacString() {
        int[] dms = toDMS();
        return String.format("%d°%02d'%02d\" %s", dms[0], dms[1], dms[2], rashi.name().substring(0, 3));
    }
}
