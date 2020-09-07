package com.dataint.diseasepo.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.diseasepo.model.form.DiseaseForm;
import com.dataint.diseasepo.model.form.DiseaseUpdateForm;
import com.dataint.diseasepo.model.param.DiseaseQueryParam;

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
