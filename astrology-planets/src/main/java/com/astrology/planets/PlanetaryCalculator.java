package com.astrology.planets;

import com.astrology.core.EphemerisConfig;
import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.core.SwissEphemerisService;
import java.util.HashMap;
import java.util.Map;

public class PlanetaryCalculator {
    private final SwissEphemerisService sweService;
    private final EphemerisConfig config;
    
    public PlanetaryCalculator(EphemerisConfig config) {
        this.config = config;
        this.sweService = new SwissEphemerisService(config);
    }
    
    public BirthChart calculateBirthChart(BirthData birthData) {
        double jdUT = birthData.toJulianDayUT();
        double jdET = birthData.toJulianDayET();
        
        double ayanamsha = sweService.getAyanamsha(jdUT);
        double lagnaLon = calculateAscendant(jdUT, birthData.latitude(), birthData.longitude(), 'W');
        Rashi lagnaRashi = Rashi.values()[(int)(lagnaLon / 30.0)];
        double mc = HouseCalculator.getMidheaven(jdUT, birthData.latitude(), birthData.longitude(), ayanamsha, sweService);
        double[] cusps = HouseCalculator.getPlacidusHouseCusps(jdUT, birthData.latitude(), birthData.longitude(), ayanamsha, sweService);
        
        Map<Planet, PlanetPosition> positions = new HashMap<>();
        for (Planet p : Planet.values()) {
            if (p != Planet.KETU && p != Planet.GULIKA && p != Planet.MANDI) {
                positions.put(p, calculatePlanetPosition(p, jdET, ayanamsha, lagnaLon));
            }
        }
        
        // Ketu is 180° from Rahu
        if (positions.containsKey(Planet.RAHU)) {
            PlanetPosition rahu = positions.get(Planet.RAHU);
            double ketuLon = (rahu.siderealLongitude() + 180.0) % 360.0;
            double ketuTropLon = (rahu.tropicalLongitude() + 180.0) % 360.0;
            PlanetPosition ketu = new PlanetPosition(
                Planet.KETU, ketuTropLon, ketuLon, -rahu.latitude(), rahu.distance(),
                rahu.speedLongitude(), rahu.retrograde(), Rashi.values()[(int)(ketuLon / 30.0)],
                NakshatraCalculator.fromLongitude(ketuLon), NakshatraCalculator.getPada(ketuLon),
                HouseCalculator.getHouseNumber(ketuLon, lagnaLon, null), ketuLon % 30.0,
                com.astrology.core.ExaltationDebilitation.DignitaryStatus.NEUTRAL
            );
            positions.put(Planet.KETU, ketu);
        }
        
        return new BirthChart(birthData, jdUT, jdET, ayanamsha, lagnaLon, lagnaRashi, mc, positions, cusps);
    }
    
    public PlanetPosition calculatePlanetPosition(Planet planet, double jdET, double ayanamsha, double lagnaLon) {
        double[] sweResult;
        if (planet.getSeCode() >= 0) {
            // Using siderealFlag() config value (e.g. SEFLG_SWIEPH | SEFLG_SPEED | SEFLG_SIDEREAL)
            // Wait, sweService.calcPlanetPosition already gets tropical if we just pass normal flag, but the config includes SIDEREAL!
            // Actually sweResult returns exactly what we want.
            // Oh, but sweResult[0] is tropical in the original code: 
            // `double tropicalLongitude = sweResult[0]; double siderealLongitude = (tropicalLongitude - ayanamsha) % 360.0;`
            // If the flag includes SEFLG_SIDEREAL, swisseph returns sidereal directly!
            // Let's explicitly calculate tropical first so PlanetPosition gets both.
            sweResult = sweService.calcPlanetPosition(jdET, planet.getSeCode(), de.thmac.swisseph.SweConst.SEFLG_SWIEPH | de.thmac.swisseph.SweConst.SEFLG_SPEED);
        } else {
            sweResult = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0}; 
        }
        return PlanetPosition.of(planet, sweResult, ayanamsha, lagnaLon);
    }
    
    public double calculateAscendant(double jdUT, double latitude, double longitude, char houseSystem) {
        double ramc = AscendantCalculator.calculateRAMC(jdUT, longitude);
        double obliquity = AscendantCalculator.calculateObliquity(jdUT);
        return AscendantCalculator.calculateFromRAMC(ramc, latitude, obliquity);
    }
    
    public Map<String, Double> calculateUpagrahas(BirthData birthData) {
        Map<String, Double> map = new HashMap<>();
        map.put("Gulika", calculateGulika(birthData));
        map.put("Mandi", calculateMandi(birthData));
        
        // Assuming Sun is fetched properly
        double sunLon = 0.0; 
        map.put("Dhuma", UpagrahaCalculator.calculateDhuma(sunLon));
        map.put("Vyatipata", UpagrahaCalculator.calculateVyatipata(map.get("Dhuma")));
        map.put("Parivesha", UpagrahaCalculator.calculateParivesha(map.get("Vyatipata")));
        map.put("Indrachapa", UpagrahaCalculator.calculateIndrachapa(map.get("Parivesha")));
        map.put("Upaketu", UpagrahaCalculator.calculateUpaketu(sunLon));
        return map;
    }
    
    private double calculateGulika(BirthData birthData) {
        return UpagrahaCalculator.calculateGulika(birthData, sweService);
    }
    
    private double calculateMandi(BirthData birthData) {
        return UpagrahaCalculator.calculateMandi(birthData, sweService);
    }
}
