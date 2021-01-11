package com.dataint.service.datapack.service;

import com.dataint.service.datapack.db.entity.Country;
import com.dataint.service.datapack.model.form.CountryForm;
import com.dataint.service.datapack.model.form.CountryUpdateForm;
import com.dataint.service.datapack.model.param.CountryQueryParam;
import com.dataint.service.datapack.model.vo.CountryVO;

import java.util.List;

public interface ICountryService {
    /**
     *
     * @param countryForm
     * @return
     */
    CountryVO addCountry(CountryForm countryForm);

    /**
     *
     * @param countryId
     * @param status
     * @return
     */
    CountryVO updateCountryStatus(Long countryId, Integer status);
    /**
     *
     * @param countryUpdateForm
     * @return
     */
    CountryVO updateCountry(CountryUpdateForm countryUpdateForm);

    /**
     *
     * @param countryId
     * @return
     */
    boolean delCountry(Long countryId);

    /**
     *
     * @param countryId
     * @return
     */
    CountryVO getCountry(Long countryId);

    /**
     *
     * @param countryQueryParam
     * @return
     */
    List<Country> getCountries(CountryQueryParam countryQueryParam);

    List<Country>  ListCountries();
}
