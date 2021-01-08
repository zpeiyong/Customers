package com.dataint.monitor.adapt;

import com.dataint.monitor.model.FocusDisease;
public interface IFocusDiseaseAdapt {
    Object  listFocusDisease(String nameCn, String showType);
    Object addFocusDisease(FocusDisease  focusDisease);
    Object listFocusDiseaseDefault();
}
