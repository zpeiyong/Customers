package com.dataint.topic.db.repository;

import com.dataint.topic.db.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Topic, Integer>, JpaSpecificationExecutor<Topic> {

    Page<Topic> getEventsByKeywordId(int keywordId, Pageable pageable);

    Topic getEventByKeywordIdAndSubTitle(int keywordId, String subTitle);

    Topic getEventByEventId(int eventId);

}
