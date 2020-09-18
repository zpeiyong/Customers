package com.dataint.monitor.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.DiseaseForm;
import com.dataint.monitor.model.form.DiseaseUpdateForm;
import com.dataint.monitor.model.param.DiseaseQueryParam;
import com.dataint.monitor.provider.DiseaseProvider;
import com.dataint.monitor.service.IDiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseServiceImpl implements IDiseaseService {
    @Autowired
    private DiseaseProvider diseaseProvider;

    @Override
    public ResultVO addDisease(DiseaseForm diseaseForm) {
        return diseaseProvider.addDisease(diseaseForm);
    }

    @Override
    public ResultVO updateDiseaseStatus(Integer diseaseId, Integer status) {
        return diseaseProvider.updateDiseaseStatus(diseaseId, status);
    }

    @Override
    public ResultVO updateDisease(DiseaseUpdateForm diseaseUpdateForm) {
        return diseaseProvider.updateDisease(diseaseUpdateForm);
    }

    @Override
    public ResultVO delDisease(Integer diseaseId) {
        return diseaseProvider.delDisease(diseaseId);
    }

    @Override
    public ResultVO getDisease(Integer diseaseId) {
        return diseaseProvider.getDisease(diseaseId);
    }

    @Override
    public ResultVO getDiseases(DiseaseQueryParam diseaseQueryParam) {
        return diseaseProvider.getDiseases(diseaseQueryParam.getKeyword(), diseaseQueryParam.getCurrent(), diseaseQueryParam.getPageSize());
    }
}