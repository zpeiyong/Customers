package com.dataint.monitor.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 报告级别表
 */
@Entity
@Table(name = "report_level")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportLevel implements Serializable {

    private static final long serialVersionUID = -8876691325244708022L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "level_name")
    private String levelName;  // 等级名称(重点疫情, 一般疫情-国际, 一般疫情-国内)

    @Column(name = "sort")
    private Integer sort;  // 报告生成顺序

}
