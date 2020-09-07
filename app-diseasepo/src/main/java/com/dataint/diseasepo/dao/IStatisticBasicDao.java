package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.StatisticBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IStatisticBasicDao extends JpaRepository<StatisticBasic, Integer> {

    StatisticBasic findFirstByOrderByStatisticStartDesc();

    StatisticBasic findByStatisticStart(Date statisticStart);
}
