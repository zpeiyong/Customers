package com.dataint.topic.model.po;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendModelEvent {

    private String nodeTime;
    private Integer articleCnt;
    private String hotValue;
    private Integer websiteCnt;
    private String keynode;
    private String keynodeType;
    private String keynodeMark;

    public JSONObject convertToJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("NODETIME", this.nodeTime);
        jsonObject.put("YQCOUNT", this.articleCnt+"");
        jsonObject.put("YQHOTVALUE", this.hotValue);
        jsonObject.put("WEBCOUNT", this.websiteCnt);
        if ("是".equals(this.keynode))
            jsonObject.put("KEYNODE", this.keynode);
        else
            jsonObject.put("KEYNODE", "否");
        jsonObject.put("KEYNODETYPE", this.keynodeType);
        jsonObject.put("KEYNODEMARK", this.keynodeMark);

        return jsonObject;
    }
}
