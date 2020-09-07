package com.dataint.diseasepo.exception.sys;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataintBaseException;

public class LoginErrorException extends DataintBaseException {
    public LoginErrorException(){
        super(BaseExceptionEnum.DATA_VER_USERNAME_PASSWORD_ERROR);
    }
    public LoginErrorException(String message){
        super(BaseExceptionEnum.DATA_VER_USERNAME_PASSWORD_ERROR,message);
    }

    public LoginErrorException(Integer code, String message) {
        super(message, code);
    }
}
