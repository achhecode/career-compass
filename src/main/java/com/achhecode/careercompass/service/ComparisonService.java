package com.achhecode.careercompass.service;

import com.achhecode.careercompass.dto.ComparisonRequest;
import com.achhecode.careercompass.model.CareerResult;
import com.achhecode.careercompass.model.ComparisonResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComparisonService {

    private final SalaryProjectionService projectionService;
    private final InsightService insightService;

    public ComparisonService(SalaryProjectionService projectionService, InsightService insightService) {
        this.projectionService = projectionService;
        this.insightService = insightService;
    }

    public ComparisonResult compare(ComparisonRequest request) {
        List<CareerResult> careers = request.careers().stream()
                .map(c -> projectionService.project(c, request.years(), request.inflationPercent()))
                .toList();

        return new ComparisonResult(
                request.years(),
                request.inflationPercent(),
                careers,
                insightService.topEarner(careers),
                insightService.generate(careers, request.years()));
    }
}
