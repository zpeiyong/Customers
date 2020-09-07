package com.dataint.service.datapack.model;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.dao.entity.Site;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class SiteVO extends BaseVO {

    public SiteVO(Site site) {
        BeanUtils.copyProperties(site, this);
    }

    private String url;

    private String nameCn;

    private String nameEn;

    private String nameOrigin;

    private String avatar;

    private String language;

    private String siteType = "1";  // 网站类型(1-官方;2-第三方)
}
