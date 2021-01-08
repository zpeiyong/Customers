package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKeywordDao extends JpaRepository<Keywords, Long> {

    Keywords findByName(String keywordName);

    @Query(value = "from Keywords k " +
            "left join TopicKeyword tk on tk.keywordId = k.id " +
            "where tk.topicId = ?1")
    List<Keywords> findAllByTopicId(Long topicId);

}
