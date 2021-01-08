package com.dataint.monitor.service;
import com.dataint.monitor.model.FocusDisease;




public interface IFocusDiseaseService {
    Object listFocusDisease(String nameCn, String showType);
    Object addFocusDisease(FocusDisease focusDisease);
    Object  listFocusDiseaseDefault();
}
