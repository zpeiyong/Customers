package com.dataint.monitor.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleBasicAdminVO extends ArticleBasicVO {

    private Integer reviewCount = 0; // 评审数量
    
    private Boolean ifInReport = false;  // 是否已加入日报

}
