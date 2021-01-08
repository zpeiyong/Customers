package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApplicationDao extends JpaRepository<Application, Long> {

    List<Application> findAllByTopicIdAndStatus(Long topicId, Integer status);

    Page<Application> findAllByStatusAndTopicNameContainingOrderByCreatedTimeDesc(Integer status, String keyword, Pageable pageable);

    Page<Application> findAllByStatusIsNotAndTopicNameContainingOrderByUpdatedTimeDesc(Integer status, String keyword, Pageable pageable);

}
