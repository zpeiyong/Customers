package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryDao extends JpaRepository<Country, Integer>, JpaSpecificationExecutor<Country> {

    Country findByNameCnOrCode(String nameCn, String code);

    Country findByNameCn(String nameCn);

    Country findByNameEn(String nameEn);
}
