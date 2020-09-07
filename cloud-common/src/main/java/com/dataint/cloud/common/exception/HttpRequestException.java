package com.dataint.cloud.common.exception;

/**
 * @description:
 * @author: Barry Song
 * @create: 2020-01-02 13:58
 **/
public class HttpRequestException extends RuntimeException {

    public HttpRequestException(String message) {
        super(message);
    }
}
