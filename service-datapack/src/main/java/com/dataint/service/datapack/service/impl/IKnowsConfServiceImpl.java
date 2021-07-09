package com.dataint.service.datapack.service.impl;

import com.dataint.service.datapack.db.dao.IKnowsConfDao;
import com.dataint.service.datapack.db.entity.KnowsConf;
import com.dataint.service.datapack.model.vo.KnowsConfVO;
import com.dataint.service.datapack.model.vo.KnowsLinkVO;
import com.dataint.service.datapack.service.IKnowsConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IKnowsConfServiceImpl implements IKnowsConfService {
    @Autowired
    private IKnowsConfDao knowsConfDao;

    @Override
    public Map<String,Object> getRelativeDataFx(Long id) {

        KnowsConf root = this.knowsConfDao.getRelativeDateFx(id.intValue());
        if (root == null) {
            return null;
        }

        List<Integer> parents = new ArrayList<Integer>(1);
        parents.add(root.getId().intValue());

        List<KnowsConf> result = new ArrayList<KnowsConf>();
        result.add(root);

        List<KnowsConf> childs = this.knowsConfDao.getChildKnowsConfs(parents);

        int symbolSize = 0;
        int category = 0;
        root.setSymbolSize(symbolSize);
        while(childs != null && !childs.isEmpty()) {

            symbolSize -= 1;
            category += 1;
            System.out.println("=====" + symbolSize);
            for (KnowsConf child : childs) {
                child.setSymbolSize(symbolSize);
                child.setCategory(category);
            }

            result.addAll(childs);
            parents = new ArrayList<Integer>(childs.size());

            for(KnowsConf knowsConf : childs) {
                parents.add(knowsConf.getId().intValue());
            }

            childs = this.knowsConfDao.getChildKnowsConfs(parents);
        }

        List<KnowsLinkVO> links = new ArrayList<KnowsLinkVO>(result.size() - 1);
        List<KnowsConfVO> nodes = new ArrayList<KnowsConfVO>(result.size());

        int index = 0;
        Map<Integer,Integer> idMap = new HashMap<Integer,Integer>(result.size());
        for(KnowsConf knowConf : result) {

            KnowsConfVO node = new KnowsConfVO(knowConf);
            node.setSymbolSize(knowConf.getSymbolSize() + symbolSize * -1 + 1);
            node.setSymbolSize(node.getSymbolSize() * 10);
            node.setCategory(knowConf.getCategory());
            nodes.add(node);
            idMap.put(node.getId().intValue(),index ++);
        }

        for(KnowsConfVO node : nodes) {

            if(node.getParentId() == 0) {

                continue;
            }

            KnowsLinkVO link = new KnowsLinkVO();

            link.setSource(idMap.get(node.getId().intValue()));
            link.setTarget(idMap.get(node.getParentId()));
            links.add(link);
        }

        Map<String,Object> map = new HashMap<String,Object>(2);

        List<Map<String,String>> categoryMap = new ArrayList<Map<String,String>>(category);

        for(int i = 0 ; i < category ; i ++) {

            Map<String,String> cateMap = new HashMap<String,String>(1);
            cateMap.put("name",i + "");
            categoryMap.add(cateMap);
        }

        map.put("nodes",nodes);
        map.put("links",links);
        map.put("categories",categoryMap);

        return map;
    }
}
