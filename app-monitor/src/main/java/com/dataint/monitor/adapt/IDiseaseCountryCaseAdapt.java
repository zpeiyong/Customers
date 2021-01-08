package com.dataint.monitor.adapt;


import com.dataint.monitor.model.DiseaseCountryCase;



public interface IDiseaseCountryCaseAdapt {
    Object listDiseaseCountry(String diseaseNameCn, String countryNameCn, String showType, String periodStart);

    /**
     *
     * @param
     * @return
     */
    Object addDieaseCountry(DiseaseCountryCase countryCase);
}
