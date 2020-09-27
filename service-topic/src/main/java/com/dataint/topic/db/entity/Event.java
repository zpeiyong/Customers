package com.dataint.topic.db.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "event")
@Accessors(chain = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Integer eventId;
//
//    @Column(name = "keyword_id")
//    private Integer keywordId;  // PoKeyword.keywordId
//
//    @Column(name = "event_time", nullable = false)
//    private Date eventTime;
//
//    @Column(name = "source_name", length = 64)
//    private String sourceName;  // source/author
//
//    @Column(name = "title", length = 128)
//    private String title;
//
//    @Column(name = "subTitle", length = 128)
//    private String subTitle;
//
//    @Column(name = "summary", length = 512)
//    private String summary;
//
//    @Column(name = "event_type", columnDefinition = "char(1) default '0'")
//    private String eventType;  // 0:Nothing; 1:舆情热度值关键节点; 2:海关介入节点; 3:企业内控或公告节点; 4:其他后续处理措施
//
//    @Column(name = "gmt_create")
//    private Date gmtCreate;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "media_type_id", insertable = true)
//    private MediaType mediaType;
}
