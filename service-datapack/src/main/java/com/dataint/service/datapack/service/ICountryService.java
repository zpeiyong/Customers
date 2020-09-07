package com.dataint.service.datapack.service;

import com.dataint.service.datapack.model.CountryVO;
import com.dataint.service.datapack.model.form.CountryForm;
import com.dataint.service.datapack.model.form.CountryUpdateForm;
import com.dataint.service.datapack.model.params.CountryQueryParam;
import org.springframework.data.domain.Page;

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
    CountryVO updateCountryStatus(Integer countryId, Integer status);
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
    boolean delCountry(Integer countryId);

    /**
     *
     * @param countryId
     * @return
     */
    CountryVO getCountry(Integer countryId);

    /**
     *
     * @param countryQueryParam
     * @return
     */
    Page<CountryVO> getCountries(CountryQueryParam countryQueryParam);
}
