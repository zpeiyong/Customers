package com.dataint.cloud.common.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.dim.ExceptionType;
import com.dataint.cloud.common.exception.DataintBaseException;

public class DataAlreadyExistException extends DataintBaseException {

    public DataAlreadyExistException() {
        super(BaseExceptionEnum.DATA_VER_ALREADY_EXIST);
    }

    public DataAlreadyExistException(String message) {
        super(BaseExceptionEnum.DATA_VER_ALREADY_EXIST, message);
    }
}
