package com.dataint.diseasepo.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "focus_country")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FocusCountry extends BasePO {

    @Column(name = "country_id")
    private Integer countryId;

}
