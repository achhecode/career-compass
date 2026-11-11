package com.achhecode.careercompass.service;

import com.achhecode.careercompass.dto.PresetDto;
import com.achhecode.careercompass.enums.CareerPreset;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PresetService {

    public List<PresetDto> allPresets() {
        return Arrays.stream(CareerPreset.values())
                .map(p -> new PresetDto(p.name(), p.getDisplayName(), p.getStartSalaryLpa(),
                        p.getAnnualGrowth(), p.getGrowthModel(), p.getBonusPercent(), p.getTaxPercent()))
                .toList();
    }
}
