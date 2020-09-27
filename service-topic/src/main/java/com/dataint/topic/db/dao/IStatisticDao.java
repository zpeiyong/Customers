package com.dataint.topic.db.dao;

import com.dataint.topic.db.IBaseInteraction;
import com.dataint.topic.db.IBaseStatistic;
import com.dataint.topic.db.ISpreadSpeed;
import com.dataint.topic.db.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IStatisticDao extends JpaRepository<Statistic, Integer> {

    Statistic getByKeywordIdAndStartTimeAndEndTimeAndStatisticType(Integer keywordId, Date startTime, Date endTime, String statisticType);

    @Query(value = "SELECT keyword_id AS keywordId, SUM(forward_cnt+comment_cnt+like_cnt) AS totalCnt " +
            "FROM statistic " +
            "WHERE statistic_type = ?1 " +
            "GROUP BY keywordId", nativeQuery = true)
    List<IBaseInteraction> countTotalInteraction(String statisticType);

    @Query(value = "SELECT keyword_id AS keywordId, SUM(article_cnt) AS articleCnt, SUM(forward_cnt) AS forwardCnt, SUM(comment_cnt) AS commentCnt, SUM(like_cnt) AS likeCnt " +
            "FROM statistic " +
            "WHERE start_time >= ?1 AND end_time <= ?2 AND statistic_type = ?3 " +
            "GROUP BY keywordId", nativeQuery = true)
    List<IBaseStatistic> countTotalStatistic(String startTime, String endTime, String statisticType);

    @Query(value = "SELECT keyword_id AS keywordId, DATE_FORMAT(start_time, '%Y-%m-%d %H:%i:%s') AS startTime, SUM(forward_cnt+comment_cnt+like_cnt) AS totalCnt " +
            "FROM statistic " +
            "WHERE keyword_id = ?1 AND statistic_type = ?2 AND start_time >= DATE_SUB(CURDATE(), INTERVAL ?3 DAY) " +
            "GROUP BY startTime", nativeQuery = true)
    List<ISpreadSpeed> countSpreadSpeed(Integer keywordId, String statisticType, Integer countDays);

    @Query(value = "FROM Statistic WHERE keywordId = ?1 AND statisticType = ?2 AND startTime in ?3 ")
    List<Statistic> countSpreadRange(Integer keywordId, String statisticType, List<Date> startTimeList);
}
