package com.astrology.api.controller;

import com.astrology.api.dto.BirthDataRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/yogas")
@Tag(name = "Yogas", description = "Planetary combination rules")
public class YogaController {
    
    @PostMapping
    public ResponseEntity<List<Object>> detectYogas(@Valid @RequestBody BirthDataRequest req) {
        return ResponseEntity.ok(List.of());
    }
    
    @PostMapping("/category/{category}")
    public ResponseEntity<List<Object>> detectByCategory(
        @PathVariable String category,
        @Valid @RequestBody BirthDataRequest req) {
        return ResponseEntity.ok(List.of());
    }
}
