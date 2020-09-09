package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "reports")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report extends BasePO {

    @Column(name = "title")
    private String title;  // 简报名称

    @Column(name = "gmt_start")
    private Date gmtStart;  // 日报起始时间

    @Column(name = "gmt_end")
    private Date gmtEnd;  // 日报结束时间

    @Column(name = "doc_path")
    private String docPath;  // 简报doc文件在服务器位置

    @Column(name = "serial_number")
    private Integer serialNumber;  // 今年期数

    @Column(name = "total_number")
    private Integer totalNumber;  // 总期数

    @Column(name = "report_type")
    private String reportType;  // 简报类型(daily, weekly, monthly, yearly)
}
