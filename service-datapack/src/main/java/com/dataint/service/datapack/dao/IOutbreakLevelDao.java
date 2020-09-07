package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.OutbreakLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOutbreakLevelDao extends JpaRepository<OutbreakLevel, Integer> {

    OutbreakLevel findByLevelCode(String levelCode);
}
