package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.Diseases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiseasesDao extends JpaRepository<Diseases, Long>, JpaSpecificationExecutor<Diseases> {

    Diseases findByNameCn(String nameCn);

    List<Diseases> findByNameCnLike(String nameCn);

    Diseases findByNameCnOrNameEn(String nameCn, String nameEn);
}
