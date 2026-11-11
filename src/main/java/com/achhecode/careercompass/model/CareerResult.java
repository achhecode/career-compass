package com.achhecode.careercompass.model;

import java.util.List;

public record CareerResult(
        String name,
        List<YearlyRecord> yearly,
        double finalGross,
        double finalNet,
        double totalGross,
        double totalNet,
        double totalRealNet) {
}
