package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.FocusCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFocusCountryDao extends JpaRepository<FocusCountry, Integer> {
}
