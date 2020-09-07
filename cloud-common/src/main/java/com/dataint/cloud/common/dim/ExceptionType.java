package com.dataint.cloud.common.dim;

public interface ExceptionType {
    /**
     * 返回 code
     *
     * @return
     */
    int getIndex();

    /**
     * 返回 message name
     *
     * @return
     */
    String getName();
}
