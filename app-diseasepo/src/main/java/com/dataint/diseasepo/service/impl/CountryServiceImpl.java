package com.dataint.diseasepo.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.CountryForm;
import com.dataint.diseasepo.model.form.CountryUpdateForm;
import com.dataint.diseasepo.model.param.CountryQueryParam;
import com.dataint.diseasepo.provider.CountryProvider;
import com.dataint.diseasepo.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements ICountryService {
    @Autowired
    private CountryProvider countryProvider;

    @Override
    public ResultVO addCountry(CountryForm countryForm) {
        return countryProvider.addCountry(countryForm);
    }

    @Override
    public ResultVO updateCountryStatus(Integer countryId, Integer status) {
        return countryProvider.updateCountryStatus(countryId, status);
    }

    @Override
    public ResultVO updateCountry(CountryUpdateForm countryUpdateForm) {
        return countryProvider.updateCountry(countryUpdateForm);
    }

    @Override
    public ResultVO delCountry(Integer countryId) {
        return countryProvider.delCountry(countryId);
    }

    @Override
    public ResultVO getCountry(Integer countryId) {
        return countryProvider.getCountry(countryId);
    }

    @Override
    public ResultVO getCountries(CountryQueryParam countryQueryParam) {
        return countryProvider.getCountries(countryQueryParam.getKeyword(), countryQueryParam.getCurrent(), countryQueryParam.getPageSize());
    }
}
