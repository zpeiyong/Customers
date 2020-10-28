package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.service.IMediaTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mediatype")
public class MediaTypeController {
    @Autowired
    private IMediaTypeService mediaTypeService;

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
    public ResultVO getMediaTypeList() {

        return ResultVO.success( mediaTypeService.getMediaTypeList());
    }

}
