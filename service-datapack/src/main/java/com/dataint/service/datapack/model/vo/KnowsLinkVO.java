package com.dataint.service.datapack.model.vo;

import com.dataint.service.datapack.db.entity.KnowsConf;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KnowsLinkVO {

    public KnowsLinkVO(KnowsConf knowsConf) {

        this.setTarget(knowsConf.getParentId());
        this.setSource(knowsConf.getId().intValue());
    }

    private int source;
    private int target;
}
