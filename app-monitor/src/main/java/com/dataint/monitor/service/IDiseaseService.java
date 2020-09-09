package com.dataint.monitor.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.DiseaseForm;
import com.dataint.monitor.model.form.DiseaseUpdateForm;
import com.dataint.monitor.model.param.DiseaseQueryParam;

public interface IDiseaseService {
    /**
     *
     * @param diseaseForm
     * @return
     */
    ResultVO addDisease(DiseaseForm diseaseForm);

    /**
     *
     * @param diseaseId
     * @param status
     * @return
     */
    ResultVO updateDiseaseStatus(Integer diseaseId, Integer status);

    /**
     *
     * @param diseaseUpdateForm
     * @return
     */
    ResultVO updateDisease(DiseaseUpdateForm diseaseUpdateForm);

    /**
     *
     * @param diseaseId
     * @return
     */
    ResultVO delDisease(Integer diseaseId);

    /**
     *
     * @param diseaseId
     * @return
     */
    ResultVO getDisease(Integer diseaseId);

    /**
     *
     * @param diseaseQueryParam
     * @return
     */
    ResultVO getDiseases(DiseaseQueryParam diseaseQueryParam);
}
