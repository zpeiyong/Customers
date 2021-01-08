package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITopicDao extends JpaRepository<Topic, Long> {

    Topic findByName(String topicName);

    List<Topic> findAllByEnableAndIfDeleted(Boolean enable,Boolean ifDeleted);

    Page<Topic> findAllByEnableAndNameContainingOrderByCreatedTimeDesc(Boolean enable, String keyword, Pageable pageable);
}
