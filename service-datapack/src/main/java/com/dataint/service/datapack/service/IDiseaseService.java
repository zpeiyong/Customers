package com.dataint.service.datapack.service;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.service.datapack.model.form.DiseaseForm;
import com.dataint.service.datapack.model.form.DiseaseUpdateForm;
import com.dataint.service.datapack.model.param.DiseaseQueryParam;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
import com.dataint.service.datapack.model.vo.DiseaseVO;
import com.dataint.service.datapack.model.vo.FocusDiseaseVO;
import org.springframework.data.domain.Page;

import java.util.List;

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
    DiseaseVO updateDiseaseStatus(Long diseaseId, Integer status);

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
    boolean delDisease(Long diseaseId);

    /**
     *
     * @param diseaseId
     * @return
     */
    DiseaseVO getDisease(Long diseaseId);

    /**
     *
     * @param diseaseQueryParam
     * @return
     */
    Page<DiseaseVO> getDiseases(DiseaseQueryParam diseaseQueryParam);

    /**
     *
     * @param diseaseQueryParam
     * @return
     */
    Page<FocusDiseaseVO> queryFocusDiseases(DiseaseQueryParam diseaseQueryParam);

    /**
     *
     * @param countryId
     * @param dateStr
     * @param pageParam
     * @return
     */
    List<DiseaseCountryCaseVO> getCasesByCountryId(Long countryId, String dateStr, PageParam pageParam);

    /**
     *
     * @param countryId
     * @param diseaseId
     * @param dateStr
     * @return
     */
    DiseaseCountryCaseVO getCasesByCidAndDid(Long countryId, Long diseaseId, String dateStr);

    /**
     * 根据疾病名称找关键字
     * @param diseaName
     * @return
     */
    List<String> getKeywordsByDisease(String diseaName);
}
