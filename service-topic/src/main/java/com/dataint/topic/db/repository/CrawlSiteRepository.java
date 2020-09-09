package com.dataint.topic.db.repository;

import com.dataint.topic.db.entity.CrawlSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CrawlSiteRepository extends JpaRepository<CrawlSite, Integer> {

    List<CrawlSite> findAllByEnable(String enable);

    @Query(value = "SELECT new Map(c.siteId, c.siteName) FROM CrawlSite c WHERE enable = ?1")
    List<Map> getCrawlSiteNames(String enable);
}
