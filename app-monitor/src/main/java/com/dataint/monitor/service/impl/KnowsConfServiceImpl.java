package com.dataint.monitor.service.impl;

import com.dataint.monitor.adapt.IKnowsConfAdapt;
import com.dataint.monitor.service.IKnowsConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowsConfServiceImpl implements IKnowsConfService {

    @Autowired
    private IKnowsConfAdapt knowsConfAdapt;


    @Override
    public Object getRelativeDataFx(Long id) {

        return knowsConfAdapt.getRelativeDataFx(id);
    }
}
