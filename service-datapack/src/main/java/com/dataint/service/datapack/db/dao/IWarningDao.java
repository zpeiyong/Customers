package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWarningDao extends JpaRepository<Warning, Long> {

//    List<Warning> findByEnable(Boolean enable);
    List<Warning> getByEnableOrderByCreatedTimeDesc(Boolean  enable);
}
