package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.MonthlyCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMonthlyCountryDao extends JpaRepository<MonthlyCountry, Integer> {

    @Query("select smc from MonthlyCountry smc " +
            "where latest = ?1 " +
            "group by smc.id, smc.countryId " +
            "order by smc.countryId, smc.diseaseSort")
    List<MonthlyCountry> findMonthlyCountryList(String latest);
}
