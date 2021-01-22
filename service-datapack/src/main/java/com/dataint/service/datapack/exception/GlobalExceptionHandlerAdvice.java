package com.dataint.service.datapack.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DefaultGlobalExceptionHandlerAdvice;
import com.dataint.cloud.common.model.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;
import java.net.UnknownServiceException;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice extends DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {UnsupportedEncodingException.class})
    public ResultVO unsupportedEncoding(UnknownServiceException ex) {
        return ResultVO.fail(BaseExceptionEnum.ENCODING_UNSUPPORTED);
    }
}
