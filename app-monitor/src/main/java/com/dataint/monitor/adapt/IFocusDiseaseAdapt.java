package com.dataint.monitor.adapt;

import com.dataint.monitor.model.FocusDisease;
public interface IFocusDiseaseAdapt {
    Object  listFocusDisease(Long id, String showType,Long current, Long pageSize);
    Object addFocusDisease(FocusDisease  focusDisease);
    Object listFocusDiseaseDefault();
}
