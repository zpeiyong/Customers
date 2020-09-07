package com.dataint.cloud.common.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.dim.ExceptionType;
import lombok.Getter;

/**
 * 频波罗基础异常定义
 **/
@Getter
public class DataintBaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 异常对应的错误类型
     */
    private final ExceptionType exceptionType;

    /**
     * 默认是系统异常
     */
    public DataintBaseException() {
        this.exceptionType = BaseExceptionEnum.SYSTEM_ERROR;
    }

    public DataintBaseException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public DataintBaseException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = new ExceptionType() {
            @Override
            public int getIndex() {
                return exceptionType.getIndex();
            }

            @Override
            public String getName() {
                return message;
            }
        };
    }

    public DataintBaseException(ExceptionType exceptionType, String message, Throwable cause) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }

    public DataintBaseException(String name, int index) {
        this.exceptionType = new ExceptionType() {
            @Override
            public int getIndex() {
                return index;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }
}
