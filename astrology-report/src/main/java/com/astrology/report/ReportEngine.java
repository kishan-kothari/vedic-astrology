package com.astrology.report;

import com.astrology.planets.BirthData;
import com.astrology.planets.BirthChart;
import com.astrology.planets.PlanetaryCalculator;
import com.astrology.divisional.DivisionalEngine;
import com.astrology.divisional.DivisionalChart;
import com.astrology.divisional.DivisionalChartSet;
import com.astrology.dasha.DashaEngine;
import com.astrology.dasha.DashaPeriod;
import com.astrology.yoga.YogaEngine;
import com.astrology.yoga.Yoga;
import com.astrology.strength.ShadbalaCalculator;
import com.astrology.strength.ShadbalaResult;
import com.astrology.strength.AshtakavargaEngine;
import com.astrology.strength.AshtakavargaResult;
import com.astrology.core.Planet;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Orchestrator class for generating complete astrology reports.
 */
public class ReportEngine {
    private final PlanetaryCalculator planetCalc;
    private final DivisionalEngine divisionalEngine;
    private final DashaEngine dashaEngine;
    private final YogaEngine yogaEngine;
    private final ShadbalaCalculator shadbalaCalculator;
    private final AshtakavargaEngine ashtakavargaEngine;

    private final PdfReportGenerator pdfGenerator;
    private final HtmlReportGenerator htmlGenerator;
    private final JsonReportGenerator jsonGenerator;

    public ReportEngine(
            PlanetaryCalculator planetCalc,
            DivisionalEngine divisionalEngine,
            DashaEngine dashaEngine,
            YogaEngine yogaEngine,
            ShadbalaCalculator shadbalaCalculator,
            AshtakavargaEngine ashtakavargaEngine) {
        this.planetCalc = planetCalc;
        this.divisionalEngine = divisionalEngine;
        this.dashaEngine = dashaEngine;
        this.yogaEngine = yogaEngine;
        this.shadbalaCalculator = shadbalaCalculator;
        this.ashtakavargaEngine = ashtakavargaEngine;

        this.pdfGenerator = new PdfReportGenerator();
        this.htmlGenerator = new HtmlReportGenerator();
        this.jsonGenerator = new JsonReportGenerator();
    }

    /**
     * Builds the complete report data from birth data.
     */
    public ReportData buildReportData(BirthData birthData, String language) {
        // Calculate natal chart
        BirthChart chart = planetCalc.calculateBirthChart(birthData);

        // Calculate divisional charts — build Map<Integer,DivisionalChart> for DivisionalChartSet
        List<DivisionalChart> allDivisional = divisionalEngine.calculateAllCharts(chart);
        Map<Integer, DivisionalChart> divisionalMap = new java.util.HashMap<>();
        for (DivisionalChart dc : allDivisional) {
            divisionalMap.put(dc.divisor(), dc);
        }
        DivisionalChartSet divisionalCharts = new DivisionalChartSet(chart, divisionalMap);

        // Dashas — Vimshottari, 30 years ahead
        List<DashaPeriod> vimshottariDashas =
            dashaEngine.calculate(chart, DashaEngine.DashaSystem.VIMSHOTTARI, 30);

        // Yoga detection
        List<Yoga> yogas = yogaEngine.detectAllYogas(chart);

        // Strength calculations
        Map<Planet, ShadbalaResult> shadbala = shadbalaCalculator.calculateAll(chart, divisionalCharts);

        // Ashtakavarga
        AshtakavargaResult ashtakavarga = ashtakavargaEngine.calculateBhinnaAshtakavarga(chart);

        // Sade Sati
        List<com.astrology.transit.SadeSatiCalculator.SadeSatiPhase> sadeSati = com.astrology.transit.SadeSatiCalculator.calculateSadeSati(chart);

        // Yogini Dasha
        com.astrology.dasha.YoginiDasha yoginiCalc = new com.astrology.dasha.YoginiDasha();
        List<com.astrology.dasha.YoginiDasha.YoginiDashaPeriod> yoginiDashas = yoginiCalc.calculateMahadashas(chart, 1);

        // Char Dasha
        com.astrology.dasha.CharDasha charCalc = new com.astrology.dasha.CharDasha();
        List<com.astrology.dasha.CharDasha.CharDashaPeriod> charDashas = charCalc.calculateMahadashas(chart);

        // KP System
        List<KPSystem.KPEntry> kpEntries = new java.util.ArrayList<>();
        for (Planet p : Planet.values()) {
            if (p == Planet.GULIKA || p == Planet.MANDI) continue;
            com.astrology.planets.PlanetPosition pos = chart.getPlanet(p);
            if (pos != null) {
                double lon = pos.siderealLongitude();
                kpEntries.add(new KPSystem.KPEntry(p.getName(), lon, KPSystem.getSignLord(lon), KPSystem.getStarLord(lon), KPSystem.getSubLord(lon)));
            }
        }
        double[] cusps = chart.getHouseCusps();
        if (cusps != null && cusps.length >= 13) {
            for (int i = 1; i <= 12; i++) {
                double lon = cusps[i];
                kpEntries.add(new KPSystem.KPEntry("Cusp " + i, lon, KPSystem.getSignLord(lon), KPSystem.getStarLord(lon), KPSystem.getSubLord(lon)));
            }
        }

        return ReportData.builder()
                .birthChart(chart)
                .divisionalCharts(divisionalCharts)
                .vimshottariDashas(vimshottariDashas)
                .yoginiDashas(yoginiDashas)
                .jaiminiDashas(charDashas)
                .yogas(yogas)
                .shadbala(shadbala)
                .ashtakavarga(ashtakavarga)
                .sadeSatiPhases(sadeSati)
                .kpEntries(kpEntries)
                .language(language)
                .build();
    }

    /**
     * Generates a PDF report to the specified file.
     */
    public void generatePdfReport(ReportData data, Path outputPath) throws IOException {
        new PdfReportGenerator().generateReport(data, outputPath);
    }

    /**
     * Generates an HTML report.
     */
    public String generateHtmlReport(ReportData data) throws IOException {
        return htmlGenerator.generateReport(data);
    }

    /**
     * Generates a JSON report.
     */
    public String generateJsonReport(ReportData data) throws JsonProcessingException {
        return jsonGenerator.generateReport(data);
    }
}
