package com.astrology.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record BirthDataRequest(
    @NotBlank String name,
    @NotNull @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTime,
    @NotBlank String timezone,
    @NotNull @DecimalMin("-90") @DecimalMax("90") Double latitude,
    @NotNull @DecimalMin("-180") @DecimalMax("180") Double longitude,
    String ayanamshaType,
    String houseSystem,
    String language // "en" or "hi"
) {}
