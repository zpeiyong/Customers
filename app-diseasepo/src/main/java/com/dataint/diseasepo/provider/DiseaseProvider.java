package com.dataint.diseasepo.provider;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.DiseaseForm;
import com.dataint.diseasepo.model.form.DiseaseUpdateForm;
import org.springframework.stereotype.Component;


@Component
public interface DiseaseProvider {


    ResultVO addDisease(DiseaseForm diseaseForm);


    ResultVO updateDiseaseStatus(Integer diseaseId, Integer status);


    ResultVO updateDisease( DiseaseUpdateForm diseaseUpdateForm);


    ResultVO delDisease(Integer diseaseId);


    ResultVO getDisease(Integer diseaseId);


    ResultVO getDiseases(String keyword, Integer current, Integer pageSize);
}
