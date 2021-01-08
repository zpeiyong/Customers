package com.dataint.topic.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.*;
import com.dataint.cloud.common.model.ResultVO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.io.UnsupportedEncodingException;
import java.net.UnknownServiceException;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice extends DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {UnsupportedEncodingException.class})
    public ResultVO unsupportedEncoding(UnknownServiceException ex) {
        return ResultVO.fail(BaseExceptionEnum.ENCODING_UNSUPPORTED);
    }

    /*
    override DefaultGlobalExceptionHandlerAdvice due to log
     */
    @Override
    public ResultVO missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return super.missingServletRequestParameterException(ex);
    }

    @Override
    public ResultVO uploadFileLimitException(MultipartException ex) {
        return super.uploadFileLimitException(ex);
    }

    @Override
    public ResultVO serviceException(MethodArgumentNotValidException ex) {
        return super.serviceException(ex);
    }

    @Override
    public ResultVO duplicateKeyException(DuplicateKeyException ex) {
        return super.duplicateKeyException(ex);
    }

    @Override
    public ResultVO baseException(DataintBaseException ex) {
        return super.baseException(ex);
    }

    @Override
    public ResultVO exception(Exception ex) {
        return super.exception(ex);
    }

    @Override
    public ResultVO throwable(Throwable ex) {
        return super.throwable(ex);
    }

    @Override
    public ResultVO fileNotFoundException(FileNotFoundException ex) {
        return super.fileNotFoundException(ex);
    }

    @Override
    public ResultVO dataNotExistException(DataNotExistException ex) {
        return super.dataNotExistException(ex);
    }

    @Override
    public ResultVO dataAlreadyExistException(DataAlreadyExistException ex) {
        return super.dataAlreadyExistException(ex);
    }
}
