package com.astrology.api.controller;

import com.astrology.api.dto.BirthDataRequest;
import com.astrology.report.ReportEngine;
import com.astrology.report.ReportData;
import com.astrology.planets.BirthData;
import com.astrology.core.AyanamshaType;
import com.astrology.core.HouseSystem;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.ZoneId;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/report")
@Tag(name = "Reports", description = "PDF, HTML, JSON reports")
public class ReportController {
    
    private final ReportEngine reportEngine;

    public ReportController(ReportEngine reportEngine) {
        this.reportEngine = reportEngine;
    }

    private BirthData convertRequest(BirthDataRequest req) {
        AyanamshaType ayanamshaType;
        try {
            ayanamshaType = req.ayanamshaType() != null ? AyanamshaType.valueOf(req.ayanamshaType().toUpperCase()) : AyanamshaType.LAHIRI;
        } catch (IllegalArgumentException e) {
            ayanamshaType = AyanamshaType.LAHIRI;
        }

        HouseSystem houseSystem;
        try {
            houseSystem = req.houseSystem() != null ? HouseSystem.valueOf(req.houseSystem().toUpperCase()) : HouseSystem.WHOLE_SIGN;
        } catch (IllegalArgumentException e) {
            houseSystem = HouseSystem.WHOLE_SIGN;
        }

        return BirthData.builder()
                .name(req.name())
                .localDateTime(req.dateTime())
                .timezone(ZoneId.of(req.timezone()))
                .latitude(req.latitude())
                .longitude(req.longitude())
                .ayanamshaType(ayanamshaType)
                .houseSystem(houseSystem)
                .build();
    }
    
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdfReport(@Valid @RequestBody BirthDataRequest req) throws Exception {
        String lang = req.language() != null ? req.language() : "en";
        ReportData data = reportEngine.buildReportData(convertRequest(req), lang);
        Path tempFile = Files.createTempFile("report", ".pdf");
        reportEngine.generatePdfReport(data, tempFile);
        byte[] pdfBytes = Files.readAllBytes(tempFile);
        Files.delete(tempFile);
        
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=\"report.pdf\"")
            .header("Content-Type", "application/pdf")
            .body(pdfBytes);
    }
    
    @PostMapping("/html")
    public ResponseEntity<String> getHtmlReport(@Valid @RequestBody BirthDataRequest req) throws Exception {
        String lang = req.language() != null ? req.language() : "en";
        ReportData data = reportEngine.buildReportData(convertRequest(req), lang);
        return ResponseEntity.ok(reportEngine.generateHtmlReport(data));
    }
    
    @PostMapping("/json")
    public ResponseEntity<String> getJsonReport(@Valid @RequestBody BirthDataRequest req) throws Exception {
        String lang = req.language() != null ? req.language() : "en";
        ReportData data = reportEngine.buildReportData(convertRequest(req), lang);
        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(reportEngine.generateJsonReport(data));
    }
}
