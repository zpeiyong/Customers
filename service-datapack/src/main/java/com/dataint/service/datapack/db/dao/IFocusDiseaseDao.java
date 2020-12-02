package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.FocusDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IFocusDiseaseDao extends JpaRepository<FocusDisease, Long>, JpaSpecificationExecutor<FocusDisease> {
}
