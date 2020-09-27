package com.dataint.topic.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class StatisticRangeVO {

    List<Date> fields;

    List<Map<String, String>> data;
}
