package com.dataint.monitor.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "map_disease")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "statistic_map_id")
    private Integer statisticMapId;

    @Column(name = "disease_name")
    private String diseaseName;

    @Column(name = "disease_cnt")
    private Integer diseaseCnt;
}
