package com.dataint.monitor.controller;

import com.dataint.monitor.adapt.IMediaTypeAdapt;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mediatype")
public class MediaTypeController {

    @Autowired
    private IMediaTypeAdapt mediaTypeAdapt;

    /**
     * 查询媒体类型
     *
     * @auther: Tim_Huo
     * @return: ResultVO
     * @date: 2020/10/28 11:00 上午
     */
    @ApiOperation(value = "查询媒体类型", notes = "查询媒体类型")
    @GetMapping("/getMediaTypeList")
    @ResponseBody
    public Object getMediaTypeList() {

        return mediaTypeAdapt.getMediaTypeList();
    }

}
