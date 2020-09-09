package com.dataint.monitor.exception.sys;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataintBaseException;

public class RoleNotExistException extends DataintBaseException {
    public RoleNotExistException (){
        super(BaseExceptionEnum.DATA_VER_ROLE_NOT_EXIST);
    }
    public RoleNotExistException(String message){
        super(BaseExceptionEnum.DATA_VER_ROLE_NOT_EXIST,message);
    }
}
