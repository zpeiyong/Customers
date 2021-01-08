package com.dataint.monitor.service;

import com.dataint.monitor.model.DiseaseCountryCase;



public interface IDiseaseCountryCaseService {
    Object listDiseaseCountry(String diseaseNameCn, String countryNameCn, String showType, String periodStart);


    Object addDieaseCountry(DiseaseCountryCase countryCase);
}
