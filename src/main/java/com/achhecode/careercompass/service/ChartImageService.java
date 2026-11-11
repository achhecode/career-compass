package com.achhecode.careercompass.service;

import com.achhecode.careercompass.constant.AppConstants;
import com.achhecode.careercompass.enums.ChartMetric;
import com.achhecode.careercompass.model.CareerResult;
import com.achhecode.careercompass.model.ComparisonResult;
import com.achhecode.careercompass.model.YearlyRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/** Renders a downloadable PNG line chart on the server using JFreeChart. */
@Service
public class ChartImageService {

    public byte[] renderPng(ComparisonResult result, ChartMetric metric) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (CareerResult career : result.careers()) {
            for (YearlyRecord record : career.yearly()) {
                dataset.addValue(metric.extract(record), career.name(), Integer.valueOf(record.year()));
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                metric.getLabel() + " - " + result.years() + " years",
                "Year",
                AppConstants.SALARY_UNIT,
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chart, AppConstants.CHART_WIDTH, AppConstants.CHART_HEIGHT);
            return out.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not render chart", e);
        }
    }
}
