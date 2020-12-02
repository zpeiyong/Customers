package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISourceDao extends JpaRepository<Source, Long> {
}
