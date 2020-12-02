package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.FocusCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFocusCountryDao extends JpaRepository<FocusCountry, Long> {

}
