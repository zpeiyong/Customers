package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventDao extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {

    Event findEventByIdAndDeleted(Integer eventId, String deleted);

    @Query(value = "select e.* from events as e where  e.id in (select a.event_id from article_event as a where a.article_id = ?1) and deleted ='N'", nativeQuery = true)
    List<Event> findAllByArticleId(Integer articleId);
}
