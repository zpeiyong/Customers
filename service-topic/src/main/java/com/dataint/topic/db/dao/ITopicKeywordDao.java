package com.dataint.topic.db.dao;

import com.dataint.topic.db.entity.TopicKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ITopicKeywordDao extends JpaRepository<TopicKeyword,Long> {

    @Query(value = "select tk.id,tk.name,tk.enable,tk.topic_id,tk.created_by,tk.created_time ,tk.updated_by,tk.updated_time, tp.name as topicName"+
            " from topic_keyword tk "+
            " left join topic tp on tk.topic_id = tp.id"+
            " where tp.if_deleted=0"+
            " order by created_time DESC",
             nativeQuery = true)
    List<Map<String,Object>> findAllList();


    @Query(value = "select tk.id,tk.name,tk.enable,tk.topic_id,tk.created_by,tk.created_time ,tk.updated_by,tk.updated_time, tp.name as topicName"+
            " from topic_keyword tk "+
            " left join topic tp on tk.topic_id = tp.id"+
            " where tp.if_deleted=1"+
            " order by created_time DESC",
            nativeQuery = true)
    List<Map<String,Object>> findAlldelList();


    List<TopicKeyword> findAllByEnableAndTopicId(Boolean enable,Long topicId);

    List<TopicKeyword> findAllByTopicId(Long topicId);


}
