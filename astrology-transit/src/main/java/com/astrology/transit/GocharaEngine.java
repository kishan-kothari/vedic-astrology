package com.astrology.transit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.astrology.planets.BirthChart;
import com.astrology.core.Planet;
import com.astrology.planets.PlanetPosition;
import com.astrology.core.Rashi;

public class GocharaEngine {

    public GocharaResult calculateGochara(Planet planet, Rashi natalMoonSign, Rashi transitSign) {
        int houseFromMoon = (transitSign.ordinal() - natalMoonSign.ordinal()) + 1;
        if (houseFromMoon <= 0) {
            houseFromMoon += 12;
        }

        String resultText = getResultText(planet, houseFromMoon);
        GocharaResult.Beneficence beneficence = getBeneficence(planet, houseFromMoon);
        int strength = getStrength(beneficence);

        return new GocharaResult(
            planet,
            natalMoonSign,
            transitSign,
            houseFromMoon,
            resultText,
            beneficence,
            strength
        );
    }

    private String getResultText(Planet planet, int house) {
        if (planet == Planet.SUN) {
            return switch (house) {
                case 1 -> "Health issues, lack of energy";
                case 2 -> "Loss of money, family troubles";
                case 3 -> "Gains, courage, good results";
                case 4 -> "Mental troubles, travel";
                case 5 -> "Stomach issues, loss of children";
                case 6 -> "Defeat of enemies, good health";
                case 7 -> "Travels, weakness";
                case 8 -> "Accidents, losses";
                case 9 -> "Loss of fortune";
                case 10 -> "Success in career (Karma)";
                case 11 -> "Gains, success";
                case 12 -> "Expenditure, foreign travel";
                default -> "Unknown";
            };
        }
        return switch (house) {
            case 3, 6, 11 -> "Good results, gains";
            case 10 -> "Mixed to good results, career focus";
            default -> "Average or mixed results";
        };
    }

    private GocharaResult.Beneficence getBeneficence(Planet planet, int house) {
        if (planet == Planet.SUN) {
            return switch (house) {
                case 3, 6, 10, 11 -> GocharaResult.Beneficence.GOOD;
                case 1, 2, 4, 5, 7, 8, 9, 12 -> GocharaResult.Beneficence.BAD;
                default -> GocharaResult.Beneficence.NEUTRAL;
            };
        }
        return switch (house) {
            case 3, 6, 11 -> GocharaResult.Beneficence.GOOD;
            default -> GocharaResult.Beneficence.NEUTRAL;
        };
    }

    private int getStrength(GocharaResult.Beneficence beneficence) {
        return switch (beneficence) {
            case VERY_GOOD -> 100;
            case GOOD -> 75;
            case NEUTRAL -> 50;
            case BAD -> 25;
            case VERY_BAD -> 0;
        };
    }

    public boolean isSadeSati(Rashi natalMoonSign, Map<Planet, PlanetPosition> transitPositions) {
        PlanetPosition saturnPos = transitPositions.get(Planet.SATURN);
        if (saturnPos == null) {
            return false;
        }
        Rashi saturnTransitSign = saturnPos.rashi();
        int diff = saturnTransitSign.ordinal() - natalMoonSign.ordinal();
        if (diff < 0) diff += 12;
        
        return diff == 11 || diff == 0 || diff == 1; // 12th, 1st, 2nd from moon
    }

    public GocharaResult.SadeSatiPhase getSadeSatiPhase(Rashi natalMoonSign, Rashi saturnTransitSign) {
        int diff = saturnTransitSign.ordinal() - natalMoonSign.ordinal();
        if (diff < 0) diff += 12;
        
        return switch (diff) {
            case 11 -> GocharaResult.SadeSatiPhase.FIRST_PEAK;
            case 0 -> GocharaResult.SadeSatiPhase.PEAK;
            case 1 -> GocharaResult.SadeSatiPhase.LAST_PEAK;
            default -> GocharaResult.SadeSatiPhase.NOT_IN_SADESATI;
        };
    }

    public List<GocharaResult> getAllGocharaResults(BirthChart natalChart, TransitChart transitChart) {
        List<GocharaResult> results = new ArrayList<>();
        PlanetPosition natalMoon = natalChart.getPositions().get(Planet.MOON);
        if (natalMoon == null) {
            return results;
        }
        
        Rashi natalMoonSign = natalMoon.rashi();
        for (Map.Entry<Planet, PlanetPosition> entry : transitChart.getTransitPositions().entrySet()) {
            results.add(calculateGochara(entry.getKey(), natalMoonSign, entry.getValue().rashi()));
        }
        return results;
    }
}
