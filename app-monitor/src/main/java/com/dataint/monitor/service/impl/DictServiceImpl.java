package com.dataint.monitor.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl implements IDictService {

//    @Autowired
//    private DictProvider dictProvider;

    @Override
    public ResultVO queryOutbreakLevels() {
        return null;
//        return dictProvider.queryOutbreakLevels();
    }

    @Override
    public ResultVO queryRegions() {
        return null;
//        return dictProvider.queryRegions();
    }

    @Override
    public ResultVO queryCountries() {
        return null;
//        return dictProvider.queryCountries();
    }

    @Override
    public ResultVO queryDiseases() {
        return null;
//        return dictProvider.queryDiseases();
    }

    @Override
    public ResultVO queryByNameCnFirst(String nameCnFirst) {
        return null;
//        return dictProvider.queryByNameCnFirst(nameCnFirst);
    }
}
