package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;

public class PdfParserVO extends BaseVO {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
