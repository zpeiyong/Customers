package com.dataint.service.datapack.service;


import com.dataint.service.datapack.db.entity.FocusDisease;
import com.dataint.service.datapack.model.form.FocusDiseaseForm;
import com.dataint.service.datapack.model.param.FocusDiseaseParam;
import com.dataint.service.datapack.model.vo.FocusDiseaseVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFocusDiseaseService {

    Page<FocusDiseaseVO> listFocusDisease(FocusDiseaseParam focusDiseaseParam);
    FocusDisease addFocusDisease(FocusDiseaseForm focusDisease);
    List<FocusDiseaseVO>  listFocusDiseaseDefault();
}
