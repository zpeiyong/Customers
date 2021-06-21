package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICountryDao extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {

    Country findByNameCnOrCode(String nameCn, String code);

    Country findByNameCn(String nameCn);

    @Query("from Country c where c.nameCn=:nameCn or c.alias like :alia")
    Country findByNameCnOrAlias(String nameCn, String alia);

    Country findByNameEn(String nameEn);

    List<Country> findAllByIdIn(List<Long> countryIdList);

    List<Country> findAllByRegionId(Long regionId);
}
