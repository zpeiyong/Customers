package com.dataint.service.datapack.service;

import com.dataint.service.datapack.model.SourceVO;
import com.dataint.service.datapack.model.form.SourceForm;
import com.dataint.service.datapack.model.form.SourceUpdateForm;

public interface ISourceService {

    SourceVO add(SourceForm sourceForm);

    SourceVO updateSource(SourceUpdateForm sourceUpdateForm);

    boolean delSource(Integer sourceId);
}
