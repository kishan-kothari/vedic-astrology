package com.astrology.api.controller;

import com.astrology.api.dto.BirthDataRequest;
import com.astrology.api.dto.ChartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Birth Chart", description = "Natal chart calculations")
public class ChartController {
    
    @PostMapping("/chart")
    @Operation(summary = "Calculate complete natal chart")
    public ResponseEntity<ChartResponse> calculateChart(@Valid @RequestBody BirthDataRequest req) {
        return ResponseEntity.ok(new ChartResponse(req, "LAHIRI", List.of(), List.of(), new ChartResponse.AscendantInfo("Aries", 0.0)));
    }
    
    @PostMapping("/chart/divisional/{divisor}")
    @Operation(summary = "Get specific divisional chart (D1-D60)")
    public ResponseEntity<Object> getDivisionalChart(
        @PathVariable int divisor,
        @Valid @RequestBody BirthDataRequest req) {
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/chart/all-divisional")
    @Operation(summary = "Get all major divisional charts")
    public ResponseEntity<Object> getAllDivisionalCharts(
        @Valid @RequestBody BirthDataRequest req) {
        return ResponseEntity.ok().build();
    }
}
