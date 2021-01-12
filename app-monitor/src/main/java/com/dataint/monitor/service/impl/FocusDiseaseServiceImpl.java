package com.dataint.monitor.service.impl;

import com.dataint.monitor.adapt.IFocusDiseaseAdapt;
import com.dataint.monitor.model.FocusDisease;
import com.dataint.monitor.service.IFocusDiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FocusDiseaseServiceImpl implements IFocusDiseaseService {
    @Autowired
    IFocusDiseaseAdapt focusDiseaseAdapt;

    @Override
    public Object listFocusDisease(Long id, String showType,Long current, Long pageSize) {
        Object listFocusDisease = focusDiseaseAdapt.listFocusDisease(id, showType,current,pageSize);
        return listFocusDisease;
    }

    @Override
    public Object addFocusDisease(FocusDisease focusDisease) {
        Object resultVO = focusDiseaseAdapt.addFocusDisease(focusDisease);
        return  resultVO;
    }

    @Override
    public Object listFocusDiseaseDefault() {
        Object resultVO = focusDiseaseAdapt.listFocusDiseaseDefault();
        return  resultVO;
    }
}
