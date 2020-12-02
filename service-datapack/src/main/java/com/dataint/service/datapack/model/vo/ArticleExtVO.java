package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.ArticleExt;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ArticleExtVO extends BaseVO {

    public ArticleExtVO(ArticleExt articleExt) {
        BeanUtils.copyProperties(articleExt, this);

//        // locationList
//        if (!StringUtils.isEmpty(articleExt.getLocations()))
//            this.locationList = Arrays.asList(articleExt.getLocations().split(Constants.SPLITTER));
//        // organizationList
//        if (!StringUtils.isEmpty(articleExt.getOrganizations()))
//            this.organizationList = Arrays.asList(articleExt.getOrganizations().split(Constants.SPLITTER));
//        // personList
//        if (!StringUtils.isEmpty(articleExt.getPersons()))
//            this.personList = Arrays.asList(articleExt.getPersons().split(Constants.SPLITTER));
//        // tableHtmlList
//        if (!StringUtils.isEmpty(articleExt.getTableHtml()))
//            this.tableHtmlList = Arrays.asList(articleExt.getTableHtml().split(Constants.SPLITTER));
    }

    private Long articleId;

//    private List<String> locationList;  // 实体提取-地点
//
//    private List<String> organizationList;  // 实体提取-机构
//
//    private List<String> personList;  // 实体提取-人物

    private List<String> tableHtmlList;  // 网页表格信息

    private Map<String, String> extMap;  // 扩展json对象

    private String snapshot;  // 快照
}
