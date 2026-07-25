package com.astrology.api.controller;

import com.astrology.api.dto.BirthDataRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transit")
@Tag(name = "Transit", description = "Transit engine and Gochara")
public class TransitController {
    
    @PostMapping
    public ResponseEntity<Object> getTransit(
        @Valid @RequestBody BirthDataRequest req,
        @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime transitDate) {
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/gochara")
    public ResponseEntity<List<Object>> getGochara(
        @Valid @RequestBody BirthDataRequest req,
        @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime transitDate) {
        return ResponseEntity.ok(List.of());
    }
}
