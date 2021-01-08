package com.dataint.monitor.service.impl;


import com.dataint.monitor.adapt.IDiseaseCountryCaseAdapt;
import com.dataint.monitor.model.DiseaseCountryCase;

import com.dataint.monitor.service.IDiseaseCountryCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DiseaseCountryCaseSericeImpl implements IDiseaseCountryCaseService {
    @Autowired
    IDiseaseCountryCaseAdapt caseProvider;

    @Override
    public Object listDiseaseCountry(String diseaseNameCn, String countryNameCn, String showType, String periodStart) {
        Object resultVO = caseProvider.listDiseaseCountry(diseaseNameCn, countryNameCn, showType, periodStart);
        return resultVO;

    }

    @Override
    public Object addDieaseCountry(DiseaseCountryCase countryCase) {
        Object resultVO = caseProvider.addDieaseCountry(countryCase);
        return resultVO;
    }
}
