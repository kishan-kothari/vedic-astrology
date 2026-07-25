package com.astrology.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ReportTest {

    private ReportData dummyData;

    @BeforeEach
    void setUp() {
        dummyData = ReportData.builder().build();
    }

    @Test
    void testJsonGeneration() throws Exception {
        JsonReportGenerator jsonGen = new JsonReportGenerator();
        String json = jsonGen.generateReport(dummyData);
        assertNotNull(json);
        assertTrue(json.contains("{") && json.contains("}"));
    }

    @Test
    void testHtmlGeneration() throws Exception {
        HtmlReportGenerator htmlGen = new HtmlReportGenerator();
        String html = htmlGen.generateReport(dummyData);
        assertNotNull(html);
        assertTrue(html.contains("<html"));
        assertTrue(html.contains("Vedic Astrology Birth Chart Analysis"));
    }

    @Test
    void testPdfGeneration() throws IOException {
        PdfReportGenerator pdfGen = new PdfReportGenerator();
        Path tempFile = Files.createTempFile("report", ".pdf");
        
        pdfGen.generateReport(dummyData, tempFile);
        
        assertTrue(Files.exists(tempFile));
        assertTrue(Files.size(tempFile) > 0);
        
        Files.deleteIfExists(tempFile);
    }
}
