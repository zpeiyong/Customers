package com.dataint.diseasepo.provider;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.SourceForm;
import com.dataint.diseasepo.model.form.SourceUpdateForm;
import org.springframework.stereotype.Component;


@Component
public interface SourceProvider {


    ResultVO add(SourceForm sourceForm);


    ResultVO updateSource(SourceUpdateForm sourceUpdateForm);


    ResultVO delSource( Integer sourceId);
}
