package com.dataint.topic.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 关键词表
 */

@Entity
@Table ( name ="keywords" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Keywords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name; // 关键词

    @Column(name = "enable", nullable = false)
    private Boolean enable = true;  // 是否可用(只对爬虫脚本过滤需要爬取的关键词有作用)
}
