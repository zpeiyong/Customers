package com.dataint.cloud.common.exception;

/**
 * @description: 找不到redis里面token
 * @author: Barry Song
 * @create: 2020-01-02 13:51
 **/
public class NotFoundRedisKeyException extends RuntimeException {

    public NotFoundRedisKeyException(String message) {
        super(message);
    }
}
