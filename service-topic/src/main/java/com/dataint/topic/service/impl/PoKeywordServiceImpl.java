package com.dataint.topic.service.impl;

import com.dataint.topic.db.entity.PoKeyword;
import com.dataint.topic.db.dao.IPoKeywordDao;
import com.dataint.topic.service.IPoKeywordService;
import com.dataint.topic.service.ISpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PoKeywordServiceImpl implements IPoKeywordService {
    @Autowired
    private IPoKeywordDao poKeywordDao;
    @Autowired
    private ISpiderService spiderService;

    private static List<String> statusTypeList = Arrays.asList("pause", "restart");

    @Override
    public Object getPoKeywordList() {
        List<PoKeyword> allList = poKeywordDao.findAll();

        List<PoKeyword> enableList = new ArrayList<>();
        List<PoKeyword> disableList = new ArrayList<>();

        for (PoKeyword poKeyword : allList) {
//            if ("1".equals(poKeyword.getEnable()))
//                enableList.add(poKeyword);
//            else
//                disableList.add(poKeyword);
        }

        Map<String, Object> rstData = new HashMap<>();
        rstData.put("enableList", enableList);
        rstData.put("disableList", disableList);

//        return ResultUtil.buildSuccResultMap(rstData);
        return null;
    }

    @Override
    public Object addPoKeyword(String keyword) {
        // 检查keyword是否已存在
        PoKeyword poKeyword = poKeywordDao.findPoKeywordByKeyword(keyword);
//        if (poKeyword != null) {
//            if ("1".equals(poKeyword.getEnable())) {
//                return ResultUtil.buildSuccResultMap("关键词 " + poKeyword.getKeyword() + " 已经在爬取列表中...");
//            } else {
//                poKeywordRepository.updateByKeywordId(poKeyword.getKeywordId(), "1");
//                return ResultUtil.buildSuccResultMap("重新启动关键词 "+poKeyword.getKeyword()+" 成功!");
//            }
//        }

        // 保存keyword
//        poKeyword = new PoKeyword(keyword);
//        poKeywordRepository.save(poKeyword);

        // publish spider tasks
//        spiderService.pubDisposeProjects(poKeyword.getKeyword());

//        return ResultUtil.buildSuccResultMap("添加关键词 " + poKeyword.getKeyword() +" 成功!");
        return null;
    }

    @Override
    public Object updateStatusById(Integer keywordId, String statusType) {
        // verify request params
        if (!statusTypeList.contains(statusType))
            return null;
//            return ResultUtil.buildFailMap("状态类型有误!");

        // update database
        if ("pause".equals(statusType))
            poKeywordDao.updateByKeywordId(keywordId, "0");
        else if ("restart".equals(statusType))
            poKeywordDao.updateByKeywordId(keywordId, "1");

//        return ResultUtil.buildSuccResultMap();
        return null;
    }

    @Override
    public Object deletePoKeywordById(Integer keywordId) {

        poKeywordDao.deleteByKeywordId(keywordId);

//        return ResultUtil.buildSuccResultMap();
        return null;
    }
}
