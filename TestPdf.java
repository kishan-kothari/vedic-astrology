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
import com.astrology.report.ReportEngine;
import com.astrology.report.ReportData;

import java.time.LocalDateTime;
import java.nio.file.Paths;

public class TestPdf {
    public static void main(String[] args) throws Exception {
        EphemerisConfig config = new EphemerisConfig("/home/kkkothari/Desktop/project/vedic-astrology/ephe", AyanamshaType.LAHIRI, HouseSystem.WHOLE_SIGN);
        PlanetaryCalculator pCalc = new PlanetaryCalculator(config);
        ReportEngine engine = new ReportEngine(
            pCalc,
            new DivisionalEngine(pCalc),
            new DashaEngine(),
            new YogaEngine(),
            new ShadbalaCalculator(),
            new AshtakavargaEngine()
        );
        
        BirthData data = new BirthData(
            "kishan kanhaiya",
            LocalDateTime.parse("1993-06-27T23:00:00"),
            "Asia/Kolkata",
            25.4795,
            74.4300
        );
        
        ReportData reportData = engine.buildReportData(data, "en");
        engine.generatePdfReport(reportData, Paths.get("test_output.pdf"));
        System.out.println("Done!");
    }
}
