package com.astrology.transit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.astrology.core.AyanamshaType;
import com.astrology.core.EphemerisConfig;
import com.astrology.core.HouseSystem;
import com.astrology.core.JulianDate;
import com.astrology.core.Planet;
import com.astrology.core.Rashi;
import com.astrology.core.SwissEphemerisService;
import com.astrology.planets.BirthChart;
import com.astrology.planets.PlanetPosition;
import com.astrology.planets.PlanetaryCalculator;

public class TransitEngine {
    private final EphemerisConfig config;
    private final SwissEphemerisService sweService;
    private final PlanetaryCalculator calculator;

    public TransitEngine(EphemerisConfig config) {
        this.config = config;
        this.sweService = new SwissEphemerisService(config);
        this.calculator = new PlanetaryCalculator(config);
    }

    /**
     * Calculate all planet positions at a given date/time without a natal chart reference.
     * Uses 0°Aries as lagna placeholder since we only need planetary positions.
     */
    public Map<Planet, PlanetPosition> calculateTransitPositions(LocalDateTime utcDateTime) {
        double jdUT = JulianDate.toJulianDay(utcDateTime);
        double jdET = jdUT + JulianDate.getDeltaT(jdUT) / 86400.0;
        double ayanamsha = sweService.getAyanamsha(jdUT);
        // Use 0.0 as placeholder lagna longitude for transit position calculation
        double lagnaLon = 0.0;

        Map<Planet, PlanetPosition> positions = new EnumMap<>(Planet.class);
        for (Planet planet : new Planet[]{
                Planet.SUN, Planet.MOON, Planet.MERCURY, Planet.VENUS, Planet.MARS,
                Planet.JUPITER, Planet.SATURN, Planet.RAHU, Planet.KETU}) {
            try {
                positions.put(planet,
                    calculator.calculatePlanetPosition(planet, jdET, ayanamsha, lagnaLon));
            } catch (Exception e) {
                // Skip planets that fail (e.g., without ephe files)
            }
        }
        return positions;
    }

    public TransitChart calculateTransit(BirthChart natalChart,
                                         LocalDateTime transitDateTime,
                                         ZoneId timezone) {
        ZonedDateTime zdt = ZonedDateTime.of(transitDateTime, timezone);
        LocalDateTime utcDateTime = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        Map<Planet, PlanetPosition> transitPositions = calculateTransitPositions(utcDateTime);
        List<TransitAspect> aspects = calculateTransitAspects(natalChart, transitPositions);
        List<TransitAspect> mutualAspects = new ArrayList<>();

        return new TransitChart(natalChart, transitPositions, transitDateTime, aspects, mutualAspects);
    }

    public List<TransitAspect> calculateTransitAspects(BirthChart natalChart,
            Map<Planet, PlanetPosition> transitPositions) {
        List<TransitAspect> aspects = new ArrayList<>();

        for (Map.Entry<Planet, PlanetPosition> transitEntry : transitPositions.entrySet()) {
            Planet transitPlanet = transitEntry.getKey();
            PlanetPosition tPos = transitEntry.getValue();

            for (Map.Entry<Planet, PlanetPosition> natalEntry : natalChart.getPositions().entrySet()) {
                Planet natalPlanet = natalEntry.getKey();
                PlanetPosition nPos = natalEntry.getValue();

                double diff = Math.abs(tPos.siderealLongitude() - nPos.siderealLongitude());
                if (diff > 180) {
                    diff = 360 - diff;
                }

                for (TransitAspect.AspectType type : TransitAspect.AspectType.values()) {
                    double orb = Math.abs(diff - type.degrees);
                    if (orb <= type.maxOrb) {
                        double exactnessPercent = 100.0 * (1.0 - (orb / type.maxOrb));
                        aspects.add(new TransitAspect(
                            transitPlanet,
                            natalPlanet,
                            tPos.siderealLongitude(),
                            nPos.siderealLongitude(),
                            orb,
                            type,
                            tPos.retrograde(),
                            false,
                            Math.max(0, exactnessPercent)
                        ));
                    }
                }
            }
        }
        return aspects;
    }

    public List<SignIngress> findSignIngresses(Planet planet, LocalDate startDate, LocalDate endDate) {
        return new ArrayList<>();
    }

    public Optional<LocalDateTime> findExactAspect(Planet transitPlanet, Planet natalPlanet,
            BirthChart natal, TransitAspect.AspectType aspectType, LocalDate searchFrom) {
        return Optional.empty();
    }

    public List<TransitEvent> getSignificantTransits(BirthChart natalChart,
            LocalDate startDate, LocalDate endDate) {
        return new ArrayList<>();
    }
}
