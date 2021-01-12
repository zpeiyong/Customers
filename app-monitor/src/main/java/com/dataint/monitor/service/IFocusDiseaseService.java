package com.dataint.monitor.service;
import com.dataint.monitor.model.FocusDisease;




public interface IFocusDiseaseService {
    Object listFocusDisease(Long id, String showType,Long current, Long pageSize);
    Object addFocusDisease(FocusDisease focusDisease);
    Object  listFocusDiseaseDefault();
}
