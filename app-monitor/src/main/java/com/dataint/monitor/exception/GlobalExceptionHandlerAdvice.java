package com.dataint.monitor.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DefaultGlobalExceptionHandlerAdvice;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.exception.sys.LoginErrorException;
import com.dataint.monitor.exception.sys.RoleNotExistException;
import com.dataint.monitor.exception.sys.UserNotExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;
import java.net.UnknownServiceException;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice extends DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {UnsupportedEncodingException.class} )
    public ResultVO unsupportedEncoding(UnknownServiceException ex) {
        return ResultVO.fail(BaseExceptionEnum.ENCODING_UNSUPPORTED);
    }

    @ExceptionHandler(value = {UserNotExistException.class})
    public  ResultVO userNotExist(UserNotExistException ex){
        return ResultVO.fail(ex.getExceptionType());
    }

    @ExceptionHandler(value = {RoleNotExistException.class})
    public ResultVO RoleNotExit(RoleNotExistException ex){
        return ResultVO.fail(ex.getExceptionType());
    }

    @ExceptionHandler(value = {LoginErrorException.class})
    public ResultVO LoginError(LoginErrorException ex){
        return ResultVO.fail(ex.getExceptionType());
    }
}
