package com.dataint.diseasepo.exception.sys;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.ResultVO;

public class RoleNotExistException extends DataintBaseException {
    public RoleNotExistException (){
        super(BaseExceptionEnum.DATA_VER_ROLE_NOT_EXIST);
    }
    public RoleNotExistException(String message){
        super(BaseExceptionEnum.DATA_VER_ROLE_NOT_EXIST,message);
    }
}
