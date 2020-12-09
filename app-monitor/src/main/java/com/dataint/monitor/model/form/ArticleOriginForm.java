package com.dataint.monitor.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
public class ArticleOriginForm extends BaseForm {

    private String origTitle;

    private String origAuthor;

    private String origEditor;

    private String origSummary;

    private String origContent;

    private List<String> origKeywordList;

    public boolean isEmpty() {
        return StringUtils.isEmpty(origTitle) &&
                StringUtils.isEmpty(origAuthor) &&
                StringUtils.isEmpty(origEditor) &&
                StringUtils.isEmpty(origSummary) &&
                StringUtils.isEmpty(origContent) &&
                CollectionUtils.isEmpty(origKeywordList);
    }
}
