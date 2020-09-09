package com.dataint.topic.db.repository;

import com.dataint.topic.db.entity.PoKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PoKeywordRepository extends JpaRepository<PoKeyword, Integer> {
    PoKeyword findPoKeywordByKeyword(String keyword);

    @Transactional
    @Modifying
    @Query("update PoKeyword p set p.enable=:enable where p.keywordId=:keywordId")
    void updateByKeywordId(@Param(value = "keywordId")Integer keywordId, @Param(value = "enable")String enable);

    @Transactional
    @Modifying
    @Query("delete from PoKeyword p where p.keywordId=:keywordId")
    void deleteByKeywordId(@Param(value = "keywordId")Integer keywordId);

    List<PoKeyword> findAllByIfSend(String ifSend);
}
