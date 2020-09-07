package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.EventSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IEventSourceDao extends JpaRepository<EventSource, Integer> {

    List<EventSource> findAllByEventId(Integer eventId);

    @Transactional
    @Modifying
    @Query("delete from EventSource es where es.eventId = ?1")
    void deleteByEventId(Integer eventId);
}
