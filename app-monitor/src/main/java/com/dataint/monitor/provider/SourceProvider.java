package com.dataint.monitor.provider;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.SourceForm;
import com.dataint.monitor.model.form.SourceUpdateForm;
import org.springframework.stereotype.Component;


@Component
public interface SourceProvider {


    ResultVO add(SourceForm sourceForm);


    ResultVO updateSource(SourceUpdateForm sourceUpdateForm);


    ResultVO delSource( Integer sourceId);
}
