package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.Country;
import com.dataint.service.datapack.dao.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISourceDao extends JpaRepository<Source, Integer> {
}
