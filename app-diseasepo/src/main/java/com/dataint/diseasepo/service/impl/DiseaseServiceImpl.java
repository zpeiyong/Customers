package com.dataint.diseasepo.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.diseasepo.model.form.DiseaseForm;
import com.dataint.diseasepo.model.form.DiseaseUpdateForm;
import com.dataint.diseasepo.model.param.DiseaseQueryParam;
import com.dataint.diseasepo.provider.DiseaseProvider;
import com.dataint.diseasepo.service.IDiseaseService;
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
