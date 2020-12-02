package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventDao extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Event findEventByIdAndDeleted(Long eventId, String deleted);

    @Query(value = "select e.* from events as e where  e.id in (select a.event_id from article_event as a where a.article_id = ?1) and deleted ='N'", nativeQuery = true)
    List<Event> findAllByArticleId(Long articleId);
}
