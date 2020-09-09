package com.dataint.topic.service;

import com.dataint.topic.db.entity.MediaType;
import com.dataint.topic.common.exception.ThinventBaseException;

import java.util.List;

public interface IMediaTypeService {

    List<MediaType> getMediaTypeList() throws ThinventBaseException;
}
