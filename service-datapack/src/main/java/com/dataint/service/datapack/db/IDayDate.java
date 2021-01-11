package com.dataint.service.datapack.db;

/**
 * 用于接收数据库按时间统计数据查询结果
 */
public interface IDayDate {

    String getDay();

    Integer getCnt();

    String countryNameCn();
}
