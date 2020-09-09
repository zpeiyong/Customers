package com.dataint.monitor.exception.sys;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataintBaseException;

public class UserNotExistException extends DataintBaseException {
    public UserNotExistException() {
        super(BaseExceptionEnum.DATA_VER_USER_NOT_EXIST);
    }
    public UserNotExistException(String message){
        super(BaseExceptionEnum.DATA_VER_USER_NOT_EXIST,message);
    }
}
