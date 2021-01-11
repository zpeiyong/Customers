package com.dataint.monitor.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.adapt.ICountryAdapt;
import com.dataint.monitor.model.form.CountryForm;
import com.dataint.monitor.model.form.CountryUpdateForm;
import com.dataint.monitor.model.param.CountryQueryParam;
import com.dataint.monitor.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements ICountryService {

    @Autowired
    private ICountryAdapt countryAdapt;


    @Override
    public ResultVO addCountry(CountryForm countryForm) {

        return null;
//        return countryProvider.addCountry(countryForm);
    }

    @Override
    public ResultVO updateCountryStatus(Integer countryId, Integer status) {

        return null;
//        return countryProvider.updateCountryStatus(countryId, status);
    }

    @Override
    public ResultVO updateCountry(CountryUpdateForm countryUpdateForm) {

        return null;
//        return countryProvider.updateCountry(countryUpdateForm);
    }

    @Override
    public ResultVO delCountry(Integer countryId) {

        return null;
//        return countryProvider.delCountry(countryId);
    }

    @Override
    public ResultVO getCountry(Integer countryId) {

        return null;
//        return countryProvider.getCountry(countryId);
    }

    @Override
    public ResultVO getCountries(CountryQueryParam countryQueryParam) {
        ResultVO countries = countryAdapt.getCountries(countryQueryParam.getKeyword());
        return  countries;
    }
}
