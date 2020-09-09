package com.dataint.topic.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SendModelStatistic {

    private String keywordId;
    private String keywords;
    private String statisticTime;
    private String score;
    private String areaScore;
    private String topicScore;
    private String commentScore;
    private String likeScore;
    private String forwardScore;
    private String consignee;
    private String consignor;
    private Integer websiteCnt;
    private Integer allLikeCnt;
    private Integer allCommentCnt;
    private Integer allForwardCnt;
    private Integer hotTimeCnt;
    private Integer govMediaCnt;
    private Integer selfMediaCnt;
    private Integer wbwxMediaCnt;
    private List<SendModelEvent> eventList;

    public JSONObject convertToJSONObject() {
        JSONArray jsonArray = new JSONArray();
        for (SendModelEvent sendModelEvent : this.eventList)
            jsonArray.add(sendModelEvent.convertToJSONObject());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("KEYWORD_ID", this.keywordId);
        jsonObject.put("KEYWORDS", this.keywords);
        jsonObject.put("YQSTAT_TIME", this.statisticTime);
        jsonObject.put("SCORE", this.score);
        jsonObject.put("AREA_SCORE", this.areaScore);
        jsonObject.put("TOPIC_SCORE", this.topicScore);
        jsonObject.put("COMMENT_SCORE", this.commentScore);
        jsonObject.put("LIKE_SCORE", this.likeScore);
        jsonObject.put("FORWARD_SCORE", this.forwardScore);
        jsonObject.put("CONSIGNEE", this.consignee);
        jsonObject.put("CONSIGNOR", this.consignor);
        jsonObject.put("YQWEBCOUNT", this.websiteCnt);
        jsonObject.put("ALLLIKE_CNT", this.allLikeCnt);
        jsonObject.put("ALLCOMMENT_CNT", this.allCommentCnt);
        jsonObject.put("ALLFORWARD_CNT", this.allForwardCnt);
        jsonObject.put("GOVMEDIACOUNT", this.govMediaCnt);
        jsonObject.put("SELFMEDIACOUNT", this.selfMediaCnt);
        jsonObject.put("WBWXMEDIACOUNT", this.wbwxMediaCnt);
        jsonObject.put("TIMENODEINFO", jsonArray);

        return jsonObject;
    }
}
