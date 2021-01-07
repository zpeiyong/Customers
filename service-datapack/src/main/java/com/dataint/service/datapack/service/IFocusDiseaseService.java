package com.dataint.service.datapack.service;


import com.dataint.service.datapack.db.entity.FocusDisease;
import com.dataint.service.datapack.model.param.FocusDiseaseParam;

import java.util.List;

public interface IFocusDiseaseService {

    List<FocusDisease>  listFocusDisease(FocusDiseaseParam focusDiseaseParam);
    FocusDisease addFocusDisease(FocusDisease  focusDisease);
    List<FocusDisease>  listFocusDiseaseDefault();
}
