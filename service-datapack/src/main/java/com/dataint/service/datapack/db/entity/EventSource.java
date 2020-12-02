package com.dataint.service.datapack.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_source_relation")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventSource extends BasePO {

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "source_id", nullable = false)
    private Long sourceId;
}
