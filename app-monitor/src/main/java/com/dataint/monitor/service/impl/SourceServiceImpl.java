package com.dataint.monitor.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.SourceForm;
import com.dataint.monitor.model.form.SourceUpdateForm;
//import com.dataint.monitor.provider.SourceProvider;
import com.dataint.monitor.service.ISourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceServiceImpl implements ISourceService {
//    @Autowired
//    private SourceProvider sourceProvider;

    @Override
    public ResultVO add(SourceForm sourceForm) {

        return null;
//        return sourceProvider.add(sourceForm);
    }

    @Override
    public ResultVO updateSource(SourceUpdateForm sourceUpdateForm) {

        return null;
//        return sourceProvider.updateSource(sourceUpdateForm);
    }

    @Override
    public ResultVO delSource(Integer sourceId) {
        return null;
//        return sourceProvider.delSource(sourceId);
    }
}
