package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.Diseases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiseasesDao extends JpaRepository<Diseases, Integer>, JpaSpecificationExecutor<Diseases> {

    Diseases findByNameCn(String nameCn);

    List<Diseases> findByNameCnLike(String nameCn);

    Diseases findByNameCnOrNameEn(String nameCn, String nameEn);
}
