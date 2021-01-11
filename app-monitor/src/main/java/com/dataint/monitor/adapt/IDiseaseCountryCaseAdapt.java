package com.dataint.monitor.adapt;
import com.dataint.monitor.model.DiseaseCountryCase;
import com.dataint.monitor.model.param.DiseaseCountryParam;

public interface IDiseaseCountryCaseAdapt {
    Object listDiseaseCountry(DiseaseCountryParam  diseaseCountryParam);

    /**
     *
     * @param
     * @return
     */
    Object addDieaseCountry(DiseaseCountryCase countryCase);

    Object getCountriesByParam(Long  diseaseId, String showType, String periodStart);
}
