package com.dataint.topic.service.impl;

import com.dataint.topic.db.entity.MediaType;
import com.dataint.topic.db.dao.IMediaTypeDao;
import com.dataint.topic.service.IMediaTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaTypeServiceImpl implements IMediaTypeService {

    @Autowired
    private IMediaTypeDao mediaTypeDao;

    @Override
    public List<MediaType> getMediaTypeList() {

        return mediaTypeDao.findAll();
    }
}
