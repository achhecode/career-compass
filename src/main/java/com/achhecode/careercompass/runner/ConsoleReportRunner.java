package com.achhecode.careercompass.runner;

import com.achhecode.careercompass.dto.ComparisonRequest;
import com.achhecode.careercompass.enums.CareerPreset;
import com.achhecode.careercompass.service.ComparisonService;
import com.achhecode.careercompass.service.TextReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/** Keeps the original console behaviour: prints Teacher vs Software at startup. */
@Component
public class ConsoleReportRunner implements ApplicationRunner {

    private final ComparisonService comparisonService;
    private final TextReportService textReportService;
    private final boolean enabled;

    public ConsoleReportRunner(ComparisonService comparisonService,
                               TextReportService textReportService,
                               @Value("${careercompass.console-report:false}") boolean enabled) {
        this.comparisonService = comparisonService;
        this.textReportService = textReportService;
        this.enabled = enabled;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!enabled) {
            return;
        }
        ComparisonRequest request = new ComparisonRequest(10, 0.0,
                List.of(CareerPreset.TEACHER.toInput(), CareerPreset.SOFTWARE_ENGINEER.toInput()));
        System.out.println(textReportService.render(comparisonService.compare(request)));
    }
}
