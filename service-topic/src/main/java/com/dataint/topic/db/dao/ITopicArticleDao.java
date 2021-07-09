package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.TopicArticle;
import io.micrometer.core.instrument.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ITopicArticleDao extends JpaRepository<TopicArticle, Integer> {


    @Query(nativeQuery = true,value="select DATE_FORMAT (tr.gmt_release,'%Y-%m-%d') gmt_date,count(tr.id)\n" +
            " from  topic_article tr \n" +
            " where tr.gmt_release is not null and DATE_FORMAT (tr.gmt_release,'%Y-%m-%d')   >= ?1 \n" +
            " GROUP BY  DATE_FORMAT(tr.gmt_release,'%Y-%m-%d')  \n" +
            " order by gmt_date")
    List<Map<String,Object>> getPopularFeelingsTj(String gmtDate);
}
