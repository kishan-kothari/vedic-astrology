package com.astrology.api.controller;

import com.astrology.api.dto.BirthDataRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/strength")
@Tag(name = "Strength", description = "Shadbala and Ashtakavarga")
public class StrengthController {
    
    @PostMapping("/shadbala")
    public ResponseEntity<Object> getShadbala(@Valid @RequestBody BirthDataRequest req) {
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/ashtakavarga")
    public ResponseEntity<Object> getAshtakavarga(@Valid @RequestBody BirthDataRequest req) {
        return ResponseEntity.ok().build();
    }
}
