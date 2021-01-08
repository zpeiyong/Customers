package com.dataint.monitor.service;

import com.dataint.monitor.model.DiseaseCountryCase;

import java.util.Date;

public interface IDiseaseCountryCaseService {
    Object listDiseaseCountry(String diseaseNameCn, String countryNameCn, String showType, Date periodStart);


    Object addDieaseCountry(DiseaseCountryCase countryCase);
}
