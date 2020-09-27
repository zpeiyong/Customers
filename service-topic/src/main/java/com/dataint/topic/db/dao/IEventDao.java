package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.Event;
import com.dataint.topic.db.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventDao extends JpaRepository<Event,Integer>{//<Topic, Integer>, JpaSpecificationExecutor<Topic> {

   // Page<Topic> getEventsByKeywordId(int keywordId, Pageable pageable);

  //  Topic getEventByKeywordIdAndSubTitle(int keywordId, String subTitle);

    Topic getEventByEventId(int eventId);

}
