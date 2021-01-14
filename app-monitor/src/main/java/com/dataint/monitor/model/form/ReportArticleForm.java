package com.dataint.monitor.model.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReportArticleForm {

    @NotNull
    private List<Long> articleIdList;

    private Long dailyLevelId;

    private Long weeklyLevelId;

    private Long monthlyLevelId;

    private Long eventLevelId;
}
