package com.dataint.service.datapack.service.impl;

import com.dataint.service.datapack.db.dao.IWarningDao;
import com.dataint.service.datapack.db.entity.Warning;
import com.dataint.service.datapack.model.vo.WarningVO;
import com.dataint.service.datapack.service.IWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarningServiceImpl implements IWarningService {

    @Autowired
    private IWarningDao warningDao;

    @Override
    public List<WarningVO> getWarningInfos() {
        List<Warning> warningList = warningDao.getByEnableOrderByCreatedTimeDesc(true);

        return warningList.stream().map(WarningVO::new).collect(Collectors.toList());
    }
}
