package com.dataint.monitor.service;

import com.dataint.monitor.model.DiseaseCountryCase;
import com.dataint.monitor.model.param.DiseaseCountryParam;

public interface IDiseaseCountryCaseService {
    Object listDiseaseCountry(DiseaseCountryParam diseaseCountryParam);


    Object addDieaseCountry(DiseaseCountryCase countryCase);

    Object  getCountriesByParam(Long  diseaseId, String showType, String periodStart);
}
