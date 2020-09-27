package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMediaTypeDao extends JpaRepository<MediaType, Integer>  {

    List<MediaType> getAllByMediaTypeId(int mediaTypeId);

    MediaType getMediaTypeByMediaTypeNameLike(String mediaTypeName);

}
