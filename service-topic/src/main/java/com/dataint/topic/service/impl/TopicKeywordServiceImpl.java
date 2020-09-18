package com.dataint.topic.service.impl;

import com.alibaba.fastjson.JSON;
import com.dataint.topic.db.dao.ITopicKeywordDao;
import com.dataint.topic.db.entity.TopicKeyword;
import com.dataint.topic.model.vo.TopicKeywordVO;
import com.dataint.topic.model.vo.TopicTreeVO;
import com.dataint.topic.service.ITopicKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TopicKeywordServiceImpl implements ITopicKeywordService {
    @Autowired
    private ITopicKeywordDao topicKeywordDao;
    @Override
    public boolean saveBatch(Long topicId, List<String> keyWordNames) {
        List<TopicKeyword> topicKeywords = keyWordNames.stream().map(names -> {
            TopicKeyword topicKeyword = new TopicKeyword();
            topicKeyword.setCreatedTime(new Date());
            topicKeyword.setTopicId(topicId);
            return topicKeyword.setName(names);
        }).collect(Collectors.toList());

        topicKeywordDao.saveAll(topicKeywords);
        return true;
    }

    @Override
    public boolean removeById(Long id) {
        topicKeywordDao.deleteById(id);
        return true;
    }

    @Override
    public List<TopicKeywordVO> topicKeyWordList() {
        List<Map<String, Object>> allByTopicId = topicKeywordDao.findAllList();
        List<TopicKeywordVO> data = JSON.parseArray(JSON.toJSONString(allByTopicId), TopicKeywordVO.class);
        ArrayList<TopicKeywordVO> topicKeywordVOS = new ArrayList<>();
        data.forEach(f -> {
            TopicKeywordVO topicKeywordVO = new TopicKeywordVO();
            TopicTreeVO topicTreeVO = new TopicTreeVO();
            Integer index = -1;
            for(int i = 0; i < topicKeywordVOS.size(); i++) {
                if(topicKeywordVOS.get(i).getTopicId() == f.getTopicId()) {
                    index = i;
                }
            }
        if (index > -1) {
            ArrayList<TopicTreeVO> children  = topicKeywordVOS.get(index).getChildren();
            topicTreeVO.setId(f.getId());
            topicTreeVO.setName(f.getName());
            topicTreeVO.setCreateTime(f.getCreatedTime());

            children.add(topicTreeVO);
            topicKeywordVOS.get(index).setChildren(children);
        } else {
            topicKeywordVO.setId(f.getId());
            topicKeywordVO.setTopicName(f.getTopicName());
            topicKeywordVO.setTopicId(f.getTopicId());

            topicTreeVO.setId(f.getId());
            topicTreeVO.setName(f.getName());
            topicTreeVO.setCreateTime(f.getCreatedTime());

            ArrayList<TopicTreeVO> topicTreeVOS = new ArrayList<>();
            topicTreeVOS.add(topicTreeVO);
            topicKeywordVO.setChildren(topicTreeVOS);
            topicKeywordVOS.add(topicKeywordVO);
        }
        });
        return topicKeywordVOS;
    }


    @Override
    public List<TopicKeywordVO> delTopicList() {
        List<Map<String, Object>> allByTopicId = topicKeywordDao.findAlldelList();
        List<TopicKeywordVO> data = JSON.parseArray(JSON.toJSONString(allByTopicId), TopicKeywordVO.class);
        ArrayList<TopicKeywordVO> topicKeywordVOS = new ArrayList<>();
        data.forEach(f -> {
            TopicKeywordVO topicKeywordVO = new TopicKeywordVO();
            TopicTreeVO topicTreeVO = new TopicTreeVO();
            Integer index = -1;
            for(int i = 0; i < topicKeywordVOS.size(); i++) {
                if(topicKeywordVOS.get(i).getTopicId() == f.getTopicId()) {
                    index = i;
                }
            }
            if (index > -1) {
                ArrayList<TopicTreeVO> children  = topicKeywordVOS.get(index).getChildren();
                topicTreeVO.setId(f.getId());
                topicTreeVO.setName(f.getName());
                topicTreeVO.setCreateTime(f.getCreatedTime());

                children.add(topicTreeVO);
                topicKeywordVOS.get(index).setChildren(children);
            } else {
                topicKeywordVO.setId(f.getId());
                topicKeywordVO.setTopicName(f.getTopicName());
                topicKeywordVO.setTopicId(f.getTopicId());

                topicTreeVO.setId(f.getId());
                topicTreeVO.setName(f.getName());
                topicTreeVO.setCreateTime(f.getCreatedTime());

                ArrayList<TopicTreeVO> topicTreeVOS = new ArrayList<>();
                topicTreeVOS.add(topicTreeVO);
                topicKeywordVO.setChildren(topicTreeVOS);
                topicKeywordVOS.add(topicKeywordVO);
            }
        });
        return topicKeywordVOS;
    }
}
