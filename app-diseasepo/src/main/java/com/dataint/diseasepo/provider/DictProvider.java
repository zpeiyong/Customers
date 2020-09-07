package com.dataint.diseasepo.provider;

import com.dataint.cloud.common.model.ResultVO;
import org.springframework.stereotype.Component;


@Component
public interface DictProvider {


    ResultVO queryOutbreakLevels();


    ResultVO queryRegions();


    ResultVO queryCountries();


    ResultVO queryDiseases();


    ResultVO queryByNameCnFirst(String nameCnFirst);

}
