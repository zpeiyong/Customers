package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.FocusDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFocusDiseaseDao extends JpaRepository<FocusDisease, Long>, JpaSpecificationExecutor<FocusDisease> {

    FocusDisease findByNameCn(String nameCn);
    List<FocusDisease> findByNameCnAndShowType(String  nameCn,String showType);
}
