package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.MonthlyTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMonthlyTimeDao extends JpaRepository<MonthlyTime, Integer> {

    List<MonthlyTime> findByMonthAndLatestOrderByDiseaseSort(String month, String latest);
}
