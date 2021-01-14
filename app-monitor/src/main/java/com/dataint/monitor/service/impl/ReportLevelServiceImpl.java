package com.dataint.monitor.service.impl;

import com.dataint.monitor.dao.IReportLevelDao;
import com.dataint.monitor.dao.entity.ReportLevel;
import com.dataint.monitor.service.IReportLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportLevelServiceImpl implements IReportLevelService {

    @Autowired
    private IReportLevelDao reportLevelDao;

    @Override
    public List<ReportLevel> getAllReportLevels() {

        return reportLevelDao.findAll();
    }
}
