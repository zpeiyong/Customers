package com.dataint.monitor.adapt;

import com.dataint.cloud.common.model.ResultVO;

public interface ICountryAdapt {
    ResultVO getCountries(String keyword);
}
