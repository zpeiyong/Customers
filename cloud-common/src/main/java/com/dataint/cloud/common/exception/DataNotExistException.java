package com.dataint.cloud.common.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;

public class DataNotExistException extends DataintBaseException {

    public DataNotExistException() {
        super(BaseExceptionEnum.DATA_VER_NOT_EXIST);
    }

    public DataNotExistException(String message) {
        super(BaseExceptionEnum.DATA_VER_NOT_EXIST, message);
    }
}
