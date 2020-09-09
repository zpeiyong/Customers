package com.dataint.topic.controller;

import com.alibaba.fastjson.JSONObject;
import com.dataint.topic.model.UpdateKeywordReq;
import com.dataint.topic.service.IPoKeywordService;
import com.dataint.topic.common.exception.ThinventBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pokeyword")
public class PoKeywordController {
    @Autowired
    private IPoKeywordService poKeywordService;

    @GetMapping("/getPoKeywordList")
    @ResponseBody
    public Object getPoKeywordList() throws ThinventBaseException {

        return poKeywordService.getPoKeywordList();
    }

    @PostMapping("/add")
    @ResponseBody
    public Object addPoKeyword(@RequestBody JSONObject jsonObject) throws ThinventBaseException {

        return poKeywordService.addPoKeyword(jsonObject.getString("poKeyword"));
    }

    @PostMapping("/update")
    @ResponseBody
    public Object updateStatusById(@RequestBody UpdateKeywordReq updateKeywordReq) throws ThinventBaseException {

        return poKeywordService.updateStatusById(updateKeywordReq.getKeywordId(), updateKeywordReq.getStatusType());
    }

    @GetMapping("/delete/{keywordId}")
    @ResponseBody
    public Object deletePoKeywordById(@PathVariable("keywordId") Integer keywordId) throws ThinventBaseException {

        return poKeywordService.deletePoKeywordById(keywordId);
    }
}
