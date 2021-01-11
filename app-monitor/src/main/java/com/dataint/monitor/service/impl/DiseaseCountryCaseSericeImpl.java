package com.dataint.monitor.service.impl;


import com.dataint.monitor.adapt.IDiseaseCountryCaseAdapt;
import com.dataint.monitor.model.DiseaseCountryCase;

import com.dataint.monitor.model.param.DiseaseCountryParam;
import com.dataint.monitor.service.IDiseaseCountryCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DiseaseCountryCaseSericeImpl implements IDiseaseCountryCaseService {
    @Autowired
    IDiseaseCountryCaseAdapt caseProvider;

    @Override
    public Object listDiseaseCountry(DiseaseCountryParam diseaseCountryParam) {
        Object resultVO = caseProvider.listDiseaseCountry(diseaseCountryParam);
        return resultVO;

    }

    @Override
    public Object addDieaseCountry(DiseaseCountryCase countryCase) {
        Object resultVO = caseProvider.addDieaseCountry(countryCase);
        return resultVO;
    }

    @Override
    public Object getCountriesByParam(Long diseaseId, String showType,String periodStart) {
        Object countriesByParam = caseProvider.getCountriesByParam(diseaseId, showType,periodStart);
        return countriesByParam;
    }
}
