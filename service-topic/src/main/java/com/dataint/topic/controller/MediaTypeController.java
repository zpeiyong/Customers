package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
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
    public ResultVO getMediaTypeList() {

        return ResultVO.success( mediaTypeService.getMediaTypeList());
    }

}
