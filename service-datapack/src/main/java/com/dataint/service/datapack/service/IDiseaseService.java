package com.dataint.service.datapack.service;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.service.datapack.model.DiseaseVO;
import com.dataint.service.datapack.model.form.DiseaseForm;
import com.dataint.service.datapack.model.form.DiseaseUpdateForm;
import com.dataint.service.datapack.model.params.DiseaseQueryParam;
import org.springframework.data.domain.Page;

public interface IDiseaseService {
    /**
     *
     * @param diseaseForm
     * @return
     */
    DiseaseVO addDisease(DiseaseForm diseaseForm);

    /**
     *
     * @param diseaseId
     * @param status
     * @return
     */
    DiseaseVO updateDiseaseStatus(Integer diseaseId, Integer status);

    /**
     *
     * @param diseaseUpdateForm
     * @return
     */
    DiseaseVO updateDisease(DiseaseUpdateForm diseaseUpdateForm);

    /**
     *
     * @param diseaseId
     * @return
     */
    boolean delDisease(Integer diseaseId);

    /**
     *
     * @param diseaseId
     * @return
     */
    DiseaseVO getDisease(Integer diseaseId);

    /**
     *
     * @param diseaseQueryParam
     * @return
     */
    Page<DiseaseVO> getDiseases(DiseaseQueryParam diseaseQueryParam);
}
