package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.MapBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IStatisticMapDao extends JpaRepository<MapBasic, Integer> {

    List<MapBasic> findByLatestOrderByDiseaseYearlyCnt(String latest);

    @Transactional
    @Modifying
    @Query("update MapBasic sm set sm.latest = 'N' where sm.latest = 'Y'")
    void updateLatest();
}
