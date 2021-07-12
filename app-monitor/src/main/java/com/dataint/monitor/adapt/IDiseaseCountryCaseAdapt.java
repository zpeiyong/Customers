package com.dataint.monitor.adapt;

import com.alibaba.fastjson.JSONObject;
import com.dataint.monitor.model.DiseaseCountryCase;
import com.dataint.monitor.model.param.DiseaseCountryParam;

public interface IDiseaseCountryCaseAdapt {
    Object listDiseaseCountry(DiseaseCountryParam diseaseCountryParam);

    /**
     *
     * @param
     * @return
     */
    Object addDiseaseCountry(DiseaseCountryCase countryCase);

    Object getCountriesByParam(Long diseaseId, String showType, String periodStart);

    JSONObject getLatestCasesByParam(Long diseaseId, Long countryId);

    Object getCountryDataTj();

    Object getDiseaseDataTj();

    Object getForCountryRisk(int disease,int country,int week);

    Object getForCountryPreDay(int disease,int country,int day);
}
