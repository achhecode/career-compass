package com.achhecode.careercompass.model;

import java.util.List;

public record ComparisonResult(
        int years,
        double inflationPercent,
        List<CareerResult> careers,
        String topEarner,
        List<String> insights) {
}
