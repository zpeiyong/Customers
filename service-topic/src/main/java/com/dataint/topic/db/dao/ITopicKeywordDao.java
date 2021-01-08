package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.TopicKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITopicKeywordDao extends JpaRepository<TopicKeyword, Long> {

    List<TopicKeyword> findAllByTopicId(Long topicId);

    void deleteAllByTopicId(Long topicId);

}
