package com.dataint.diseasepo.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.CountryForm;
import com.dataint.diseasepo.model.form.CountryUpdateForm;
import com.dataint.diseasepo.model.param.CountryQueryParam;

public interface ICountryService {

    ResultVO addCountry(CountryForm countryForm);

    ResultVO updateCountryStatus(Integer countryId, Integer status);

    ResultVO updateCountry(CountryUpdateForm countryUpdateForm);

    ResultVO delCountry(Integer countryId);

    ResultVO getCountry(Integer countryId);

    ResultVO getCountries(CountryQueryParam countryQueryParam);
}
