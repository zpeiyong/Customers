package com.dataint.topic.service.impl;

import com.dataint.topic.db.entity.PoKeyword;
import com.dataint.topic.db.repository.PoKeywordRepository;
import com.dataint.topic.service.IPoKeywordService;
import com.dataint.topic.service.ISpiderService;
import com.dataint.topic.utils.ResultUtil;
import com.dataint.topic.common.exception.ThinventBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PoKeywordServiceImpl implements IPoKeywordService {
    @Autowired
    private PoKeywordRepository poKeywordRepository;
    @Autowired
    private ISpiderService spiderService;

    private static List<String> statusTypeList = Arrays.asList("pause", "restart");

    @Override
    public Object getPoKeywordList() throws ThinventBaseException {
        List<PoKeyword> allList = poKeywordRepository.findAll();

        List<PoKeyword> enableList = new ArrayList<>();
        List<PoKeyword> disableList = new ArrayList<>();

        for (PoKeyword poKeyword : allList) {
            if ("1".equals(poKeyword.getEnable()))
                enableList.add(poKeyword);
            else
                disableList.add(poKeyword);
        }

        Map<String, Object> rstData = new HashMap<>();
        rstData.put("enableList", enableList);
        rstData.put("disableList", disableList);

        return ResultUtil.buildSuccResultMap(rstData);
    }

    @Override
    public Object addPoKeyword(String keyword) throws ThinventBaseException {
        // 检查keyword是否已存在
        PoKeyword poKeyword = poKeywordRepository.findPoKeywordByKeyword(keyword);
        if (poKeyword != null) {
            if ("1".equals(poKeyword.getEnable())) {
                return ResultUtil.buildSuccResultMap("关键词 " + poKeyword.getKeyword() + " 已经在爬取列表中...");
            } else {
                poKeywordRepository.updateByKeywordId(poKeyword.getKeywordId(), "1");
                return ResultUtil.buildSuccResultMap("重新启动关键词 "+poKeyword.getKeyword()+" 成功!");
            }
        }

        // 保存keyword
        poKeyword = new PoKeyword(keyword);
        poKeywordRepository.save(poKeyword);

        // publish spider tasks
        spiderService.pubDisposeProjects(poKeyword.getKeyword());

        return ResultUtil.buildSuccResultMap("添加关键词 " + poKeyword.getKeyword() +" 成功!");
    }

    @Override
    public Object updateStatusById(Integer keywordId, String statusType) throws ThinventBaseException {
        // verify request params
        if (!statusTypeList.contains(statusType))
            return ResultUtil.buildFailMap("状态类型有误!");

        // update database
        if ("pause".equals(statusType))
            poKeywordRepository.updateByKeywordId(keywordId, "0");
        else if ("restart".equals(statusType))
            poKeywordRepository.updateByKeywordId(keywordId, "1");

        return ResultUtil.buildSuccResultMap();
    }

    @Override
    public Object deletePoKeywordById(Integer keywordId) throws ThinventBaseException {

        poKeywordRepository.deleteByKeywordId(keywordId);

        return ResultUtil.buildSuccResultMap();
    }
}
