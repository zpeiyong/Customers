package com.dataint.topic.controller;

import com.dataint.topic.service.IMediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mediatype")
public class MediaTypeController {
    @Autowired
    private IMediaTypeService mediaTypeService;

    @GetMapping("/getMediaTypeList")
    @ResponseBody
    public Object getMediaTypeList() {

        return mediaTypeService.getMediaTypeList();
    }

}
