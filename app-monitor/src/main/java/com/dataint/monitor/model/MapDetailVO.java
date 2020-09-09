package com.dataint.monitor.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MapDetailVO {

    private String diseaseName;  // 疫情名称

    private Integer caseCnt;  // 病例数

    private Integer deathCnt;  // 死亡数

    private JSONArray articleList;  // 概况舆情列表
}
