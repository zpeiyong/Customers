package com.dataint.monitor.service.impl;

import com.dataint.monitor.adapt.IDiseaseCountryCaseAdapt;
import com.dataint.monitor.model.DiseaseCountryCase;
import com.dataint.monitor.model.param.DiseaseCountryParam;
import com.dataint.monitor.service.IDiseaseCountryCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseCountryCaseServiceImpl implements IDiseaseCountryCaseService {
    @Autowired
    private IDiseaseCountryCaseAdapt dcCaseAdapt;

    @Override
    public Object listDiseaseCountry(DiseaseCountryParam diseaseCountryParam) {
        Object resultVO = dcCaseAdapt.listDiseaseCountry(diseaseCountryParam);
        return resultVO;

    }

    @Override
    public Object addDiseaseCountry(DiseaseCountryCase countryCase) {
        Object resultVO = dcCaseAdapt.addDiseaseCountry(countryCase);
        return resultVO;
    }

    @Override
    public Object getCountriesByParam(Long diseaseId, String showType,String periodStart) {
        Object countriesByParam = dcCaseAdapt.getCountriesByParam(diseaseId, showType,periodStart);
        return countriesByParam;
    }

    @Override
    public Object getLatestCasesByParam(Long diseaseId, Long countryId) {

        return dcCaseAdapt.getLatestCasesByParam(diseaseId, countryId);
    }
}
