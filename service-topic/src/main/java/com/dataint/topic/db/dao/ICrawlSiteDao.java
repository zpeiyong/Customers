package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.CrawlSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ICrawlSiteDao extends JpaRepository<CrawlSite, Integer> {

    List<CrawlSite> findAllByEnable(String enable);

    @Query(value = "SELECT new Map(c.id, c.nameCn) FROM CrawlSite c WHERE enable = ?1")
    List<Map> getCrawlSiteNames(String enable);
}
