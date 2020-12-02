package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.EventSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IEventSourceDao extends JpaRepository<EventSource, Long> {

    List<EventSource> findAllByEventId(Long eventId);

    @Transactional
    @Modifying
    @Query("delete from EventSource es where es.eventId = ?1")
    void deleteByEventId(Long eventId);
}
