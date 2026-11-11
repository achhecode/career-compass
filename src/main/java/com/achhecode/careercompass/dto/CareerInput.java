package com.achhecode.careercompass.dto;

import com.achhecode.careercompass.enums.GrowthModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * One career path the user wants to compare.
 *
 * @param name           label shown on charts and reports
 * @param startSalaryLpa year-1 gross salary in LPA
 * @param annualGrowth   percent (COMPOUND_PERCENT) or LPA amount (FIXED_AMOUNT_LPA) added each year
 * @param growthModel    how annualGrowth is applied
 * @param bonusPercent   yearly bonus as % of gross salary
 * @param taxPercent     flat tax % applied on gross + bonus (simple estimate)
 */
public record CareerInput(
        @NotBlank(message = "Career name is required") String name,
        @PositiveOrZero(message = "Start salary cannot be negative") double startSalaryLpa,
        double annualGrowth,
        GrowthModel growthModel,
        @PositiveOrZero(message = "Bonus cannot be negative") double bonusPercent,
        @PositiveOrZero @Max(value = 100, message = "Tax cannot exceed 100%") double taxPercent) {

    public CareerInput {
        if (growthModel == null) {
            growthModel = GrowthModel.COMPOUND_PERCENT;
        }
    }
}
