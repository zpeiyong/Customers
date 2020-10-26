package com.dataint.topic.db.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Deprecated
@Data
@Entity
@Table(name = "pokeyword")
@Accessors(chain = true)
public class PoKeyword {
    public PoKeyword() {}

    public PoKeyword(String keyword) {
        this.setKeyword(keyword);
        this.setEnable("1");
        this.setIfSend("0");
    }

    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer keywordId;
//
    @Column(name = "keyword")
    private String keyword;

    @Column(name = "enable", columnDefinition = "char(1) default '1'")
    private String enable;  // 1: enable; 0: disable

    @Column(name = "if_send", columnDefinition = "char(1) default '0'")
    private String ifSend;  // 1. send to bdCenter; 0: not send
}
