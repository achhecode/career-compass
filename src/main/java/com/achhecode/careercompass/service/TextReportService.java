package com.achhecode.careercompass.service;

import com.achhecode.careercompass.constant.AppConstants;
import com.achhecode.careercompass.enums.ChartMetric;
import com.achhecode.careercompass.model.CareerResult;
import com.achhecode.careercompass.model.ComparisonResult;
import com.achhecode.careercompass.util.MoneyUtil;
import org.springframework.stereotype.Service;

import java.util.Locale;

/** Renders a ComparisonResult as a plain-text report (console, API, or copy-paste). */
@Service
public class TextReportService {

    private static final int W = AppConstants.NAME_COLUMN_WIDTH;

    public String render(ComparisonResult result) {
        StringBuilder sb = new StringBuilder();
        String bar = "=".repeat(70);

        sb.append(bar).append('\n');
        sb.append("  ").append(AppConstants.APP_NAME).append(" - CAREER EARNINGS COMPARISON\n");
        sb.append(bar).append('\n');
        sb.append(String.format(Locale.US, "Years: %d | Inflation: %.1f%% | Unit: %s (lakh rupees)%n%n",
                result.years(), result.inflationPercent(), AppConstants.SALARY_UNIT));

        table(sb, "ANNUAL GROSS SALARY (LPA)", result, ChartMetric.ANNUAL_GROSS);
        table(sb, "ANNUAL TAKE-HOME (LPA)", result, ChartMetric.ANNUAL_NET);
        table(sb, "CUMULATIVE TAKE-HOME (LPA-years = lakh)", result, ChartMetric.CUMULATIVE_NET);

        summary(sb, result);

        sb.append("\nKEY TAKEAWAYS\n");
        result.insights().forEach(i -> sb.append(" * ").append(i).append('\n'));
        sb.append(bar).append('\n');
        return sb.toString();
    }

    private void table(StringBuilder sb, String title, ComparisonResult result, ChartMetric metric) {
        sb.append(title).append('\n');
        sb.append(String.format("%-6s", "Year"));
        for (CareerResult c : result.careers()) {
            sb.append(String.format("%-" + W + "s", shorten(c.name())));
        }
        sb.append('\n').append("-".repeat(6 + W * result.careers().size())).append('\n');

        for (int i = 0; i < result.years(); i++) {
            sb.append(String.format("%-6d", i + 1));
            for (CareerResult c : result.careers()) {
                sb.append(String.format(Locale.US, "%-" + W + ".2f", metric.extract(c.yearly().get(i))));
            }
            sb.append('\n');
        }
        sb.append('\n');
    }

    private void summary(StringBuilder sb, ComparisonResult result) {
        sb.append("SUMMARY\n");
        sb.append(String.format("%-" + W + "s%-14s%-14s%-16s%-16s%n",
                "Career", "Final gross", "Final net", "Total net", "Total real net"));
        sb.append("-".repeat(W + 60)).append('\n');
        for (CareerResult c : result.careers()) {
            sb.append(String.format(Locale.US, "%-" + W + "s%-14.2f%-14.2f%-16.2f%-16.2f%n",
                    shorten(c.name()), c.finalGross(), c.finalNet(), c.totalNet(), c.totalRealNet()));
        }
        sb.append(String.format("%nTop earner: %s (%s)%n", result.topEarner(),
                MoneyUtil.readable(result.careers().stream()
                        .mapToDouble(CareerResult::totalNet).max().orElse(0))));
    }

    private String shorten(String name) {
        return name.length() < W ? name : name.substring(0, W - 2) + "..";
    }
}
