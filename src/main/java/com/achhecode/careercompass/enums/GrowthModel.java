package com.achhecode.careercompass.enums;

public enum GrowthModel {
    /** Salary grows by a percentage each year (e.g. 7% compounding). */
    COMPOUND_PERCENT("Percent per year"),
    /** Salary grows by a fixed number of LPA each year (e.g. +1.5 LPA). */
    FIXED_AMOUNT_LPA("Fixed LPA per year");

    private final String label;

    GrowthModel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
