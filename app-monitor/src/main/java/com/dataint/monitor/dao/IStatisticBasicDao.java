package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.StatisticBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IStatisticBasicDao extends JpaRepository<StatisticBasic, Integer> {

    StatisticBasic findFirstByOrderByStatisticStartDesc();

    StatisticBasic findByStatisticStart(Date statisticStart);
}
