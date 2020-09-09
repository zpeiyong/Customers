package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.ImpactRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImpactRankDao extends JpaRepository<ImpactRank, Integer> {

    List<ImpactRank> findByLatestOrderByRank(String latest);
}
