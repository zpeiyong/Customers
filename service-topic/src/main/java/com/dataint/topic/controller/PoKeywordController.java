package com.dataint.topic.controller;

import com.alibaba.fastjson.JSONObject;
import com.dataint.topic.model.UpdateKeywordReq;
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
    public Object getPoKeywordList() {

        return poKeywordService.getPoKeywordList();
    }

    @PostMapping("/add")
    @ResponseBody
    public Object addPoKeyword(@RequestBody JSONObject jsonObject) {

        return poKeywordService.addPoKeyword(jsonObject.getString("poKeyword"));
    }

    @PostMapping("/update")
    @ResponseBody
    public Object updateStatusById(@RequestBody UpdateKeywordReq updateKeywordReq) {

        return poKeywordService.updateStatusById(updateKeywordReq.getKeywordId(), updateKeywordReq.getStatusType());
    }

    @GetMapping("/delete/{keywordId}")
    @ResponseBody
    public Object deletePoKeywordById(@PathVariable("keywordId") Integer keywordId) {

        return poKeywordService.deletePoKeywordById(keywordId);
    }
}
