package com.dataint.service.datapack.db.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "knows_conf")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowsConf {

    @Id
    @Column(name = "id")
    private Long id;    //ID

    @Column(name = "disease_id")
    private Integer diseaseId;    //传染病ID

    @Column(name = "node_name")
    private String nodeName;    //传染病名字

    @Column(name = "node_content")
    private String nodeContent;    //传染病ID

    @Column(name = "parent_id")
    private Integer parentId;    //传染病父节点


    @Transient
    private int symbolSize;


    @Transient
    private int category;


}
