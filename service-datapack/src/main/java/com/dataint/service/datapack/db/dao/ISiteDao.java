package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISiteDao extends JpaRepository<Site, Long> {

    Site findByNameCnAndUrl(String nameCn, String url);
}
