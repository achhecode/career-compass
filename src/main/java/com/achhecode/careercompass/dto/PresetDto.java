package com.achhecode.careercompass.dto;

import com.achhecode.careercompass.enums.GrowthModel;

public record PresetDto(
        String key,
        String displayName,
        double startSalaryLpa,
        double annualGrowth,
        GrowthModel growthModel,
        double bonusPercent,
        double taxPercent) {
}
