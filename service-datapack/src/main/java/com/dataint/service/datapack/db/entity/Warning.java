package com.dataint.service.datapack.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 预警表
 */
@Entity
@Table(name = "warning")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warning extends BasePO {

    @Column(name = "title")
    private String title;  // 预警标题

    /*
    预警等级:
        1: Ⅰ级, 可能死亡30人以上的特别重大事故(红色)
        2: Ⅱ级, 可能死亡10~29人的重大事故(橙色)
        3: Ⅲ级, 可能死亡3~9人的较大事故(黄色)
        4: Ⅳ级, 可能死亡1~2人的一般事故(蓝色)
     */
    @Column(name = "level")
    private Integer level;

    @Column(name = "enable")
    private Boolean enable;  // 是否有效(true:有效,false:无效)

    @Column(name = "reason")
    private String reason;  // 预警原因

    @Column(name = "created_time")
    private Date createdTime;  // 疫情事件开始时间
}
