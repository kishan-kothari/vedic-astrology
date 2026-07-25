package com.astrology.report;

import com.astrology.planets.BirthData;
import com.astrology.core.EphemerisConfig;
import com.astrology.core.AyanamshaType;
import com.astrology.core.HouseSystem;
import com.astrology.planets.PlanetaryCalculator;
import com.astrology.divisional.DivisionalEngine;
import com.astrology.dasha.DashaEngine;
import com.astrology.yoga.YogaEngine;
import com.astrology.strength.ShadbalaCalculator;
import com.astrology.strength.AshtakavargaEngine;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.nio.file.Paths;
import java.nio.file.Files;

public class PdfReportGeneratorTest {
    @Test
    public void testPdfGeneration() throws Exception {
        EphemerisConfig config = new EphemerisConfig("/home/kkkothari/Desktop/project/vedic-astrology/ephe", AyanamshaType.LAHIRI, HouseSystem.WHOLE_SIGN, 0);
        PlanetaryCalculator pCalc = new PlanetaryCalculator(config);
        ReportEngine engine = new ReportEngine(
            pCalc,
            new DivisionalEngine(),
            new DashaEngine(),
            new YogaEngine(),
            new ShadbalaCalculator(),
            new AshtakavargaEngine()
        );
        
        BirthData data = new BirthData(
            "kishan kanhaiya",
            LocalDateTime.parse("1993-06-27T23:00:00"),
            ZoneId.of("Asia/Kolkata"),
            25.4795,
            74.4300,
            AyanamshaType.LAHIRI,
            HouseSystem.WHOLE_SIGN
        );
        
        ReportData reportData = engine.buildReportData(data, "en");
        engine.generatePdfReport(reportData, Paths.get("target/test_output.pdf"));
    }
}
