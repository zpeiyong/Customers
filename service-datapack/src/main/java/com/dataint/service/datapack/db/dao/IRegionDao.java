package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegionDao extends JpaRepository<Region, Long> {

    List<Region> findAllByOrderByNameCn();
}
