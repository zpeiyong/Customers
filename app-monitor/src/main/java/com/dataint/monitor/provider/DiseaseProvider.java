package com.dataint.monitor.provider;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.DiseaseForm;
import com.dataint.monitor.model.form.DiseaseUpdateForm;
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
