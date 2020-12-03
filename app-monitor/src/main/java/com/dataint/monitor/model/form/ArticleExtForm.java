package com.dataint.monitor.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Data
public class ArticleExtForm extends BaseForm {

//    private Integer id;  // 扩展表id, 存入时为空
//
//    private Integer articleId;  // 文章id, 存入时为空

    private List<String> locationList;  // 实体提取-地点

    private List<String> organizationList;  // 实体提取-机构

    private List<String> personList;  // 实体提取-人物

    private List<String> tableHtmlList;  // 网页表格信息

    private Map<String, String> extMap;  // 扩展json对象

    private String snapshot;  // 快照

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(locationList) &&
                CollectionUtils.isEmpty(organizationList) &&
                CollectionUtils.isEmpty(personList) &&
                CollectionUtils.isEmpty(tableHtmlList) &&
                CollectionUtils.isEmpty(extMap);
    }
}
