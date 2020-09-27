package com.dataint.topic.controller;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.model.form.UpdateKeywordForm;
import com.dataint.topic.service.IPoKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pokeyword")
public class PoKeywordController {
    @Autowired
    private IPoKeywordService poKeywordService;

    @GetMapping("/getPoKeywordList")
    @ResponseBody
    public ResultVO getPoKeywordList() {

        return ResultVO.success(poKeywordService.getPoKeywordList());
    }

    @PostMapping("/add")
    @ResponseBody
    public ResultVO addPoKeyword(@RequestBody JSONObject jsonObject) {

        return ResultVO.success(poKeywordService.addPoKeyword(jsonObject.getString("poKeyword")));
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultVO updateStatusById(@RequestBody UpdateKeywordForm updateKeywordReq) {

        return ResultVO.success(poKeywordService.updateStatusById(updateKeywordReq.getKeywordId(), updateKeywordReq.getStatusType()));
    }

    @GetMapping("/delete/{keywordId}")
    @ResponseBody
    public ResultVO deletePoKeywordById(@PathVariable("keywordId") Integer keywordId) {

        return ResultVO.success( poKeywordService.deletePoKeywordById(keywordId));
    }
}
