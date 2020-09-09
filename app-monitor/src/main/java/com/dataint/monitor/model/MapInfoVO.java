package com.dataint.monitor.model;

import com.dataint.monitor.dao.entity.MapBasic;
import com.dataint.monitor.dao.entity.MapDisease;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MapInfoVO {

    public MapInfoVO(MapBasic mapBasic) {
        BeanUtils.copyProperties(mapBasic, this);
        if (!CollectionUtils.isEmpty(mapBasic.getDetailList())) {
            List<String> nameList = mapBasic.getDetailList().stream()
                    .map(MapDisease::getDiseaseName).collect(Collectors.toList());
            if (nameList.size() <= 5)
                this.diseaseNameList = nameList;
            else
                this.diseaseNameList = nameList.subList(0, 5);
        }
    }

    private Integer countryId;  // 国家表主键Id

    private String countryCode;  // 国家

    private String countryName;  // 国家名称

    private Integer diseaseYearlyCnt;  // 今年该国家舆情总数

    private List<String> diseaseNameList;  // 国家对应疫情传染病名称列表
}
