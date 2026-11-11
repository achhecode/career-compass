package com.achhecode.careercompass.controller;

import com.achhecode.careercompass.constant.ApiPaths;
import com.achhecode.careercompass.dto.ComparisonRequest;
import com.achhecode.careercompass.enums.ChartMetric;
import com.achhecode.careercompass.enums.GrowthModel;
import com.achhecode.careercompass.model.ComparisonResult;
import com.achhecode.careercompass.service.ChartImageService;
import com.achhecode.careercompass.service.ComparisonService;
import com.achhecode.careercompass.service.PresetService;
import com.achhecode.careercompass.service.TextReportService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ApiPaths.BASE)
public class ComparisonApiController {

    private final ComparisonService comparisonService;
    private final TextReportService textReportService;
    private final ChartImageService chartImageService;
    private final PresetService presetService;

    public ComparisonApiController(ComparisonService comparisonService,
                                   TextReportService textReportService,
                                   ChartImageService chartImageService,
                                   PresetService presetService) {
        this.comparisonService = comparisonService;
        this.textReportService = textReportService;
        this.chartImageService = chartImageService;
        this.presetService = presetService;
    }

    /** Presets, chart metrics and growth models used to build the UI. */
    @GetMapping(ApiPaths.META)
    public Map<String, Object> meta() {
        List<Map<String, String>> metrics = Arrays.stream(ChartMetric.values())
                .map(m -> Map.of("key", m.name(), "label", m.getLabel(), "field", m.getJsonField()))
                .toList();
        List<Map<String, String>> models = Arrays.stream(GrowthModel.values())
                .map(g -> Map.of("key", g.name(), "label", g.getLabel()))
                .toList();
        return Map.of("presets", presetService.allPresets(), "metrics", metrics, "growthModels", models);
    }

    @PostMapping(ApiPaths.COMPARE)
    public ComparisonResult compare(@Valid @RequestBody ComparisonRequest request) {
        return comparisonService.compare(request);
    }

    @PostMapping(value = ApiPaths.COMPARE_TEXT, produces = MediaType.TEXT_PLAIN_VALUE)
    public String compareAsText(@Valid @RequestBody ComparisonRequest request) {
        return textReportService.render(comparisonService.compare(request));
    }

    @PostMapping(value = ApiPaths.COMPARE_CHART, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] compareAsChart(@Valid @RequestBody ComparisonRequest request,
                                 @RequestParam(defaultValue = "CUMULATIVE_NET") ChartMetric metric) {
        return chartImageService.renderPng(comparisonService.compare(request), metric);
    }
}
