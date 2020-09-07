package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionDao extends JpaRepository<Region, Integer> {
}
