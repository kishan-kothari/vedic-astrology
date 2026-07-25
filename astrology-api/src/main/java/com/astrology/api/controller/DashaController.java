package com.astrology.api.controller;

import com.astrology.api.dto.BirthDataRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/dasha")
@Tag(name = "Dashas", description = "Planetary period systems")
public class DashaController {
    
    @PostMapping("/vimshottari")
    public ResponseEntity<Object> getVimshottari(
        @Valid @RequestBody BirthDataRequest req,
        @RequestParam(defaultValue = "25") int yearsAhead) {
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/yogini")
    public ResponseEntity<Object> getYoginiDasha(
        @Valid @RequestBody BirthDataRequest req,
        @RequestParam(defaultValue = "10") int yearsAhead) {
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/jaimini")
    public ResponseEntity<Object> getJaiminiDasha(
        @Valid @RequestBody BirthDataRequest req) {
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/current")
    public ResponseEntity<Object> getCurrentDasha(
        @Valid @RequestBody BirthDataRequest req,
        @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate asOf) {
        return ResponseEntity.ok().build();
    }
}
