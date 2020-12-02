package com.dataint.service.datapack.service;

import com.dataint.service.datapack.model.form.SourceForm;
import com.dataint.service.datapack.model.form.SourceUpdateForm;
import com.dataint.service.datapack.model.vo.SourceVO;

public interface ISourceService {

    SourceVO add(SourceForm sourceForm);

    SourceVO updateSource(SourceUpdateForm sourceUpdateForm);

    boolean delSource(Long sourceId);
}
