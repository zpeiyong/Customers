package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.FocusDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFocusDiseaseDao extends JpaRepository<FocusDisease, Long>, JpaSpecificationExecutor<FocusDisease> {

    FocusDisease findByNameCn(String nameCn);

    @Query("from FocusDisease fd where fd.nameCn=:nameCn or fd.alias like :alias")
    FocusDisease findByNameCnOrAlias(String nameCn,String alias);

    List<FocusDisease> findByNameCnAndShowType(String nameCn, String showType);


}
