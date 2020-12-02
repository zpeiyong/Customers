package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMediaTypeDao extends JpaRepository<MediaType, Long> {
}
