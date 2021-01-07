package com.dataint.service.datapack.service;

import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.param.DiseaseCountryParam;


import java.util.List;

public interface IDiseaseCountryCaseService {
    /**
     *
     * @param
     * @return
     */

    List<DiseaseCountryCase> listDiseaseCountry(DiseaseCountryParam diseaseCountryParam);

    /**
     *
     * @param
     * @return
     */
    DiseaseCountryCase addDieaseCountry(DiseaseCountryCase  countryCase);
}
