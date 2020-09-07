package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.service.IArticleOriginService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/articleOrigin")
@Slf4j
public class ArticleOriginController {

    @Autowired
    private IArticleOriginService articleOriginService;

    @ApiOperation(value = "获取舆情源语言信息", notes = "获取舆情源语言信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping(value = "/{id}")
    public ResultVO get(@PathVariable Integer id) {
        log.debug("get with id:{}", id);

        return ResultVO.success(articleOriginService.get(id));
    }
}
