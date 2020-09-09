package com.dataint.monitor.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DefaultGlobalExceptionHandlerAdvice;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.exception.sys.LoginErrorException;
import com.dataint.monitor.exception.sys.RoleNotExistException;
import com.dataint.monitor.exception.sys.UserNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;
import java.net.UnknownServiceException;

@RestControllerAdvice
@Slf4j(topic = "appLog")
public class GlobalExceptionHandlerAdvice extends DefaultGlobalExceptionHandlerAdvice {
@ExceptionHandler(value = {UnsupportedEncodingException.class} )
public ResultVO unsupportedEncoding(UnknownServiceException ex) {
    log.error(ex.getMessage(), ex);
    return ResultVO.fail(BaseExceptionEnum.ENCODING_UNSUPPORTED);
}

@ExceptionHandler(value = {UserNotExistException.class})
    public  ResultVO userNotExist(UserNotExistException ex){
    log.error(ex.getExceptionType().getName(),ex);
    return ResultVO.fail(ex.getExceptionType());
}

@ExceptionHandler(value = {RoleNotExistException.class})
    public ResultVO RoleNotExit(RoleNotExistException ex){
    log.error(ex.getExceptionType().getName(),ex);
    return ResultVO.fail(ex.getExceptionType());
}

@ExceptionHandler(value = {LoginErrorException.class})
    public ResultVO LoginError(LoginErrorException ex){
    log.error(ex.getExceptionType().getName(),ex);
    return ResultVO.fail(ex.getExceptionType());
}
}
