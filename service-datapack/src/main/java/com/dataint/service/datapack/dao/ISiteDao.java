package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISiteDao extends JpaRepository<Site, Integer> {

    Site findByNameCnAndUrl(String nameCn, String url);
}
