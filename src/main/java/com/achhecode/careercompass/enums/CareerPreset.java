package com.achhecode.careercompass.enums;

import com.achhecode.careercompass.dto.CareerInput;

/**
 * Ready-made starting points. All numbers are ILLUSTRATIVE assumptions -
 * users are expected to edit them to match their own offer / reality.
 */
public enum CareerPreset {
    TEACHER("Teacher", 6.5, 3.0, GrowthModel.COMPOUND_PERCENT, 0, 0),
    SOFTWARE_ENGINEER("Software Engineer", 25.0, 7.0, GrowthModel.COMPOUND_PERCENT, 0, 0),
    GOVERNMENT_OFFICER("Government Officer", 9.0, 4.0, GrowthModel.COMPOUND_PERCENT, 0, 0),
    BANK_OFFICER("Bank Officer", 8.0, 5.0, GrowthModel.COMPOUND_PERCENT, 0, 0),
    DOCTOR("Doctor", 12.0, 8.0, GrowthModel.COMPOUND_PERCENT, 0, 0),
    CUSTOM("Custom Career", 10.0, 5.0, GrowthModel.COMPOUND_PERCENT, 0, 0);

    private final String displayName;
    private final double startSalaryLpa;
    private final double annualGrowth;
    private final GrowthModel growthModel;
    private final double bonusPercent;
    private final double taxPercent;

    CareerPreset(String displayName, double startSalaryLpa, double annualGrowth,
                 GrowthModel growthModel, double bonusPercent, double taxPercent) {
        this.displayName = displayName;
        this.startSalaryLpa = startSalaryLpa;
        this.annualGrowth = annualGrowth;
        this.growthModel = growthModel;
        this.bonusPercent = bonusPercent;
        this.taxPercent = taxPercent;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getStartSalaryLpa() {
        return startSalaryLpa;
    }

    public double getAnnualGrowth() {
        return annualGrowth;
    }

    public GrowthModel getGrowthModel() {
        return growthModel;
    }

    public double getBonusPercent() {
        return bonusPercent;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    public CareerInput toInput() {
        return new CareerInput(displayName, startSalaryLpa, annualGrowth, growthModel, bonusPercent, taxPercent);
    }
}
