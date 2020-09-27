package com.dataint.topic.db.dao;

import com.dataint.topic.db.IBaseMedia;
import com.dataint.topic.db.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMediaDao extends JpaRepository<Media, Integer> {

    @Query(value = "SELECT a.keyword_id AS keywordId, m.media_type_id AS mediaTypeId, COUNT(1) AS mediaCnt " +
            "FROM media m " +
            "LEFT JOIN article a ON m.media_name = a.author " +
            "WHERE a.gmt_release >= ?1 AND gmt_release <= ?2 " +
            "GROUP BY keywordId, mediaTypeId", nativeQuery = true)
    List<IBaseMedia> countMedia(String startTime, String endTime);

    @Query(value = "SELECT a.keyword_id AS keywordId, COUNT(1) AS mediaCnt " +
            "FROM article a " +
            "LEFT JOIN crawl_site c ON a.site_id = c.site_id " +
            "WHERE a.gmt_release >= ?1 AND a.gmt_release <= ?2 AND c.site_name LIKE %?3% " +
            "GROUP BY keywordId", nativeQuery = true)
    List<IBaseMedia> countWbWxMedia(String startTime, String endTime, String mediaName);

}
