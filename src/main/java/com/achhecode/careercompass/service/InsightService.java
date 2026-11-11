package com.achhecode.careercompass.service;

import com.achhecode.careercompass.model.CareerResult;
import com.achhecode.careercompass.util.MoneyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Turns raw numbers into plain-language takeaways that help people decide. */
@Service
public class InsightService {

    public String topEarner(List<CareerResult> careers) {
        return careers.stream()
                .max(Comparator.comparingDouble(CareerResult::totalNet))
                .map(CareerResult::name)
                .orElse("-");
    }

    public List<String> generate(List<CareerResult> careers, int years) {
        List<CareerResult> ranked = new ArrayList<>(careers);
        ranked.sort(Comparator.comparingDouble(CareerResult::totalNet).reversed());

        CareerResult winner = ranked.get(0);
        List<String> insights = new ArrayList<>();
        insights.add(String.format("%s earns the most take-home over %d years: %s.",
                winner.name(), years, MoneyUtil.readable(winner.totalNet())));

        for (int i = 1; i < ranked.size(); i++) {
            CareerResult other = ranked.get(i);
            double gap = winner.totalNet() - other.totalNet();
            insights.add(String.format("%s earns %s more than %s over %d years.",
                    winner.name(), MoneyUtil.readable(gap), other.name(), years));
            insights.add(crossoverMessage(winner, other));
        }

        CareerResult highestFinal = careers.stream()
                .max(Comparator.comparingDouble(CareerResult::finalNet)).orElse(winner);
        insights.add(String.format("In year %d the highest annual take-home is %s at %.2f LPA.",
                years, highestFinal.name(), highestFinal.finalNet()));
        return insights;
    }

    /** First year from which the winner stays ahead of the other path in cumulative take-home. */
    private String crossoverMessage(CareerResult winner, CareerResult other) {
        int lastBehindIndex = -1;
        for (int i = 0; i < winner.yearly().size(); i++) {
            if (winner.yearly().get(i).cumulativeNet() <= other.yearly().get(i).cumulativeNet()) {
                lastBehindIndex = i;
            }
        }
        if (lastBehindIndex == -1) {
            return String.format("%s is ahead of %s from year 1.", winner.name(), other.name());
        }
        return String.format("%s is behind %s early on, but overtakes it in cumulative earnings from year %d.",
                winner.name(), other.name(), lastBehindIndex + 2);
    }
}
