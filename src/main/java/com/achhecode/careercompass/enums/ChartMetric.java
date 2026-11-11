package com.achhecode.careercompass.enums;

import com.achhecode.careercompass.model.YearlyRecord;

import java.util.function.ToDoubleFunction;

/** What to plot on the chart / show in the comparison table. */
public enum ChartMetric {
    ANNUAL_GROSS("Annual gross salary", "gross", YearlyRecord::gross),
    ANNUAL_NET("Annual take-home", "net", YearlyRecord::net),
    REAL_NET("Take-home (inflation adjusted)", "realNet", YearlyRecord::realNet),
    CUMULATIVE_NET("Cumulative take-home", "cumulativeNet", YearlyRecord::cumulativeNet),
    CUMULATIVE_GROSS("Cumulative gross", "cumulativeGross", YearlyRecord::cumulativeGross);

    private final String label;
    private final String jsonField;
    private final ToDoubleFunction<YearlyRecord> extractor;

    ChartMetric(String label, String jsonField, ToDoubleFunction<YearlyRecord> extractor) {
        this.label = label;
        this.jsonField = jsonField;
        this.extractor = extractor;
    }

    public String getLabel() {
        return label;
    }

    public String getJsonField() {
        return jsonField;
    }

    public double extract(YearlyRecord record) {
        return extractor.applyAsDouble(record);
    }
}
