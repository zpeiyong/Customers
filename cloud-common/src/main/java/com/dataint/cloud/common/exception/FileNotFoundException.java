package com.dataint.cloud.common.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;

public class FileNotFoundException extends DataintBaseException {

    public FileNotFoundException() {
        super(BaseExceptionEnum.DATA_VER_NOT_EXIST);
    }

    public FileNotFoundException(String message) {
        super(BaseExceptionEnum.DATA_VER_NOT_EXIST, message);
    }
}
