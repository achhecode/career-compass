package com.achhecode.careercompass.dto;

import com.achhecode.careercompass.constant.AppConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ComparisonRequest(
        @Min(AppConstants.MIN_YEARS) @Max(AppConstants.MAX_YEARS) Integer years,
        @PositiveOrZero Double inflationPercent,
        @NotNull @Size(min = AppConstants.MIN_CAREERS, max = AppConstants.MAX_CAREERS,
                message = "Compare between 2 and 8 careers") @Valid List<CareerInput> careers) {

    public ComparisonRequest {
        if (years == null) {
            years = AppConstants.DEFAULT_YEARS;
        }
        if (inflationPercent == null) {
            inflationPercent = AppConstants.DEFAULT_INFLATION_PERCENT;
        }
    }
}
