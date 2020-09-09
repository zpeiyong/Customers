package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.MonthlyTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMonthlyTimeDao extends JpaRepository<MonthlyTime, Integer> {

    List<MonthlyTime> findByMonthAndLatestOrderByDiseaseSort(String month, String latest);
}
