package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.KnowsConf;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Random;


@Data
@NoArgsConstructor
public class KnowsConfVO extends BaseVO {

    private static final Random random = new Random(System.currentTimeMillis());

    public KnowsConfVO(KnowsConf knowsConf) {

        this.setId(knowsConf.getId());
        this.setName(knowsConf.getNodeName());
      //  this.setValue(knowsConf.getNodeContent());
        this.setDiseaseId(knowsConf.getDiseaseId());
        this.setParentId(knowsConf.getParentId());





        this.setX(random.nextInt(400) * -1);
        this.setY(random.nextInt(200));
    }

    private Long id;

    private Integer diseaseId;

    private String name;

    private String value;  // 传染病内容概述

    private Integer parentId;  // 传染病父节点

    private int x;
    private int y;
    private int symbolSize;

    private int category;

}
