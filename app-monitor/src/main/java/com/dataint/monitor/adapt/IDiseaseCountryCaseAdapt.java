package com.dataint.monitor.adapt;


import com.dataint.monitor.model.DiseaseCountryCase;

import java.util.Date;

public interface IDiseaseCountryCaseAdapt {
    Object listDiseaseCountry(String diseaseNameCn, String countryNameCn, String showType, Date periodStart);

    /**
     *
     * @param
     * @return
     */
    Object addDieaseCountry(DiseaseCountryCase countryCase);
}
