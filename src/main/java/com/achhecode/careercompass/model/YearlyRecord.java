package com.achhecode.careercompass.model;

/** One year of one career. All money values are in LPA (lakh rupees). */
public record YearlyRecord(
        int year,
        double gross,
        double hike,
        double bonus,
        double tax,
        double net,
        double realNet,
        double cumulativeGross,
        double cumulativeNet) {
}
