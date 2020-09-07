package com.dataint.cloud.common.exception;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.model.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@Slf4j
public class DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResultVO missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("missing servlet request parameter exception:{}", ex.getMessage());
        ex.printStackTrace();
        return ResultVO.fail(BaseExceptionEnum.ARGUMENT_NOT_VALID);
    }

    @ExceptionHandler(value = {MultipartException.class})
    public ResultVO uploadFileLimitException(MultipartException ex) {
        log.error("upload file size limit:{}", ex.getMessage());
        ex.printStackTrace();
        return ResultVO.fail(BaseExceptionEnum.UPLOAD_FILE_SIZE_LIMIT);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResultVO serviceException(MethodArgumentNotValidException ex) {
        log.error("service exception:{}", ex.getMessage());
        ex.printStackTrace();
        return ResultVO.fail(BaseExceptionEnum.ARGUMENT_NOT_VALID, ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    public ResultVO duplicateKeyException(DuplicateKeyException ex) {
        log.error("primary key duplication exception:{}", ex.getMessage());
        ex.printStackTrace();
        return ResultVO.fail(BaseExceptionEnum.DUPLICATE_PRIMARY_KEY);
    }

    @ExceptionHandler(value = {DataintBaseException.class})
    public ResultVO baseException(DataintBaseException ex) {
        log.error("base exception:{}", ex.getExceptionType().getName());
        ex.printStackTrace();
        return ResultVO.fail(ex.getExceptionType());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResultVO exception(Exception ex) {
        log.error("exception:{}", ex.getMessage());
        ex.printStackTrace();
        return ResultVO.fail(ex);
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResultVO throwable(Throwable ex) {
        log.error("throwable:{}", ex.getMessage());
        ex.printStackTrace();
        return ResultVO.fail(ex);
    }

    @ExceptionHandler(value = {FileNotFoundException.class})
    public ResultVO fileNotFoundException(FileNotFoundException fnfe) {
        log.error("data not exist exception:{}", fnfe.getExceptionType().getName());
        return ResultVO.fail(fnfe.getExceptionType());
    }

    @ExceptionHandler(value = {DataNotExistException.class})
    public ResultVO dataNotExistException(DataNotExistException dnee) {
        log.error("data not exist exception:{}", dnee.getExceptionType().getName());
        return ResultVO.fail(dnee.getExceptionType());
    }

    @ExceptionHandler(value = {DataAlreadyExistException.class})
    public ResultVO dataAlreadyExistException(DataAlreadyExistException daee) {
        log.error("data already exist exception:{}", daee.getExceptionType().getName());
        return ResultVO.fail(daee.getExceptionType());
    }
}