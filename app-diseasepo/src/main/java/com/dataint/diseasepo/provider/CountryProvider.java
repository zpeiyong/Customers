package com.dataint.diseasepo.provider;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.CountryForm;
import com.dataint.diseasepo.model.form.CountryUpdateForm;
import org.springframework.stereotype.Component;


@Component
public interface CountryProvider {
    ResultVO addCountry( CountryForm countryForm);


    ResultVO updateCountryStatus( Integer countryId,Integer status);


    ResultVO updateCountry(CountryUpdateForm countryUpdateForm);


    ResultVO delCountry(Integer countryId);


    ResultVO getCountry(Integer countryId);


    ResultVO getCountries( String keyword, Integer current, Integer pageSize);
}
