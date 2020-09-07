package com.dataint.diseasepo.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.SourceForm;
import com.dataint.diseasepo.model.form.SourceUpdateForm;
import com.dataint.diseasepo.provider.SourceProvider;
import com.dataint.diseasepo.service.ISourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceServiceImpl implements ISourceService {
    @Autowired
    private SourceProvider sourceProvider;

    @Override
    public ResultVO add(SourceForm sourceForm) {

        return sourceProvider.add(sourceForm);
    }

    @Override
    public ResultVO updateSource(SourceUpdateForm sourceUpdateForm) {
        return sourceProvider.updateSource(sourceUpdateForm);
    }

    @Override
    public ResultVO delSource(Integer sourceId) {
        return sourceProvider.delSource(sourceId);
    }
}
