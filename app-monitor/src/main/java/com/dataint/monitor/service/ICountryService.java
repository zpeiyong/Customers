package com.dataint.monitor.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.CountryForm;
import com.dataint.monitor.model.form.CountryUpdateForm;
import com.dataint.monitor.model.param.CountryQueryParam;

public interface ICountryService {

    ResultVO addCountry(CountryForm countryForm);

    ResultVO updateCountryStatus(Integer countryId, Integer status);

    ResultVO updateCountry(CountryUpdateForm countryUpdateForm);

    ResultVO delCountry(Integer countryId);

    ResultVO getCountry(Integer countryId);

    ResultVO getCountries(CountryQueryParam countryQueryParam);
}
