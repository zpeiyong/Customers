package com.dataint.monitor.service;

import com.dataint.monitor.model.DiseaseCountryCase;
import com.dataint.monitor.model.param.DiseaseCountryParam;

import java.util.List;

public interface IDiseaseCountryCaseService {
    Object listDiseaseCountry(DiseaseCountryParam diseaseCountryParam);

    Object addDiseaseCountry(DiseaseCountryCase countryCase);

    Object  getCountriesByParam(Long diseaseId, String showType, String periodStart);

    Object getLatestCasesByParam(Long diseaseId, Long countryId);

    Object getCountryDataTj();

    Object getDiseaseDataTj();

    Object getForCountryRisk(int disease,int country,int week);

    /**
     *
     * @param diseaseId
     * @param countryId
     * @param day
     * @return
     */
    public Object getForCountryPreDay(int diseaseId, int countryId, int day);
}
