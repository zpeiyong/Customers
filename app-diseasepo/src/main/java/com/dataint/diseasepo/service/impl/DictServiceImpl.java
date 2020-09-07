package com.dataint.diseasepo.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.provider.DictProvider;
import com.dataint.diseasepo.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    private DictProvider dictProvider;

    @Override
    public ResultVO queryOutbreakLevels() {
        return dictProvider.queryOutbreakLevels();
    }

    @Override
    public ResultVO queryRegions() {
        return dictProvider.queryRegions();
    }

    @Override
    public ResultVO queryCountries() {
        return dictProvider.queryCountries();
    }

    @Override
    public ResultVO queryDiseases() {
        return dictProvider.queryDiseases();
    }

    @Override
    public ResultVO queryByNameCnFirst(String nameCnFirst) {
        return dictProvider.queryByNameCnFirst(nameCnFirst);
    }
}
