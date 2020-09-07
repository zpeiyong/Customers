package com.dataint.diseasepo.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.SourceForm;
import com.dataint.diseasepo.model.form.SourceUpdateForm;

public interface ISourceService {

    ResultVO add(SourceForm sourceForm);

    ResultVO updateSource(SourceUpdateForm sourceUpdateForm);

    ResultVO delSource(Integer sourceId);

}
