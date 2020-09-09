package com.dataint.topic.db.repository;

import com.dataint.topic.db.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {

    Page<Event> getEventsByKeywordId(int keywordId, Pageable pageable);

    Event getEventByKeywordIdAndSubTitle(int keywordId, String subTitle);

    Event getEventByEventId(int eventId);

}
