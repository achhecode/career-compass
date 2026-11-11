package com.achhecode.careercompass.service;

import com.achhecode.careercompass.dto.CareerInput;
import com.achhecode.careercompass.enums.GrowthModel;
import com.achhecode.careercompass.model.CareerResult;
import com.achhecode.careercompass.model.YearlyRecord;
import com.achhecode.careercompass.util.MoneyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** Pure calculation: turns one CareerInput into a year-by-year projection. */
@Service
public class SalaryProjectionService {

    public CareerResult project(CareerInput input, int years, double inflationPercent) {
        List<YearlyRecord> yearly = new ArrayList<>(years);

        double gross = input.startSalaryLpa();
        double cumulativeGross = 0;
        double cumulativeNet = 0;
        double totalReal = 0;
        double lastNet = 0;

        for (int year = 1; year <= years; year++) {
            double hike = 0;
            if (year > 1) {
                double previous = gross;
                gross = nextSalary(gross, input);
                hike = gross - previous;
            }

            double bonus = gross * input.bonusPercent() / 100.0;
            double taxable = gross + bonus;
            double tax = taxable * input.taxPercent() / 100.0;
            double net = taxable - tax;
            double real = net / Math.pow(1 + inflationPercent / 100.0, year - 1);

            cumulativeGross += taxable;
            cumulativeNet += net;
            totalReal += real;
            lastNet = net;

            yearly.add(new YearlyRecord(
                    year,
                    MoneyUtil.round2(gross),
                    MoneyUtil.round2(hike),
                    MoneyUtil.round2(bonus),
                    MoneyUtil.round2(tax),
                    MoneyUtil.round2(net),
                    MoneyUtil.round2(real),
                    MoneyUtil.round2(cumulativeGross),
                    MoneyUtil.round2(cumulativeNet)));
        }

        return new CareerResult(
                input.name(),
                yearly,
                MoneyUtil.round2(gross),
                MoneyUtil.round2(lastNet),
                MoneyUtil.round2(cumulativeGross),
                MoneyUtil.round2(cumulativeNet),
                MoneyUtil.round2(totalReal));
    }

    private double nextSalary(double current, CareerInput input) {
        double next = input.growthModel() == GrowthModel.COMPOUND_PERCENT
                ? current * (1 + input.annualGrowth() / 100.0)
                : current + input.annualGrowth();
        return Math.max(next, 0);
    }
}
