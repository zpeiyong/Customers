package com.dataint.monitor.adapt.impl;

import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IKnowsConfAdapt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IKnowsConfAdaptImpl implements IKnowsConfAdapt {

    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public Object getRelativeDataFx(Long id) {///knowsConf/getRelativeDateF
        String url ="http://" +  baseUrl + "/knowsConf/getRelativeDataFx";

        HashMap<String, String> map = new HashMap<>();
        if (id!=null) {
            map.put("id", id.toString());
        }
        return GetPostUtil.sendGet(url,map);
        }
}
