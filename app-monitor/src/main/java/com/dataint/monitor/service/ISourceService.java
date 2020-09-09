package com.dataint.monitor.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.SourceForm;
import com.dataint.monitor.model.form.SourceUpdateForm;

public interface ISourceService {

    ResultVO add(SourceForm sourceForm);

    ResultVO updateSource(SourceUpdateForm sourceUpdateForm);

    ResultVO delSource(Integer sourceId);

}
