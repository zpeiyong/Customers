package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITopicDao extends JpaRepository<Topic, Long> {
}
