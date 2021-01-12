package com.dataint.service.datapack.service;


import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.form.DiseaseCountryForm;
import com.dataint.service.datapack.model.param.DiseaseCountryParam;
import com.dataint.service.datapack.model.vo.CountryVO;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
import org.springframework.data.domain.Page;


import java.text.ParseException;

import java.util.List;

public interface IDiseaseCountryCaseService {
    /**
     *
     * @param
     * @return
     */

    Page<DiseaseCountryCaseVO> listDiseaseCountry(DiseaseCountryParam diseaseCountryParam);

    /**
     *
     * @param
     * @return
     */
    DiseaseCountryCase addDieaseCountry(DiseaseCountryForm countryCase);



    List<CountryVO> getCountriesByParam(Long diseaseId, String showType, String periodStart) throws  ParseException;
}
