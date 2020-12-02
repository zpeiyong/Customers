package com.dataint.monitor.adapt.impl;

import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IMediaTypeAdapt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MediaTypeAdaptImpl implements IMediaTypeAdapt {

    @Value("${service.topic.baseUrl}")
    private String baseUrl;


    @Override
    public Object getMediaTypeList() {
        String url ="http://" +  baseUrl + "/mediatype/getMediaTypeList";
        return GetPostUtil.sendGet(url);
    }
}
