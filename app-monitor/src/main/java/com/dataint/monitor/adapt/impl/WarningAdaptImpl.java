package com.dataint.monitor.adapt.impl;

import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IWarningAdapt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WarningAdaptImpl implements IWarningAdapt {

    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public Object getWarningInfos() {
        String url ="http://" +  baseUrl + "/warning/getWarningInfos";
        return GetPostUtil.sendGet(url);
    }
}
