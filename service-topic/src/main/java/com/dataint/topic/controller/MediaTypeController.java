package com.dataint.topic.controller;

import com.dataint.topic.service.IMediaTypeService;
import com.dataint.topic.common.exception.ThinventBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mediatype")
public class MediaTypeController {
    @Autowired
    private IMediaTypeService mediaTypeService;

    @GetMapping("/getMediaTypeList")
    @ResponseBody
    public Object getMediaTypeList() throws ThinventBaseException {

        return mediaTypeService.getMediaTypeList();
    }

}
