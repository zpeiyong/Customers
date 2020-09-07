package com.dataint.cloud.common.model;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.dim.ExceptionType;
import com.dataint.cloud.common.exception.DataintBaseException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResultVO<T> {
    private static final int REQUEST_SUCCESS_CODE = 200;
    private static final String REQUEST_SUCCESS_MSG = "请求成功";

    private int code;  // 处理结果code
    private String msg;  // 处理结果描述信息
    private T data;  // 处理结果数据信息

    public ResultVO() {

    }

    /**
     *
     * @param exceptionType
     */
    public ResultVO(ExceptionType exceptionType) {
        this.code = exceptionType.getIndex();
        this.msg = exceptionType.getName();
    }

    /**
     * @param exceptionType
     * @param data
     */
    public ResultVO(ExceptionType exceptionType, T data) {
        this(exceptionType);
        this.data = data;
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    private ResultVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static ResultVO success(Object data) {
        return new ResultVO<>(REQUEST_SUCCESS_CODE, REQUEST_SUCCESS_MSG, data);
    }

    public static ResultVO success(Object data, Pagination pagination) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("list", data);
        responseBody.put("pagination", pagination);
        return new ResultVO<>(REQUEST_SUCCESS_CODE, REQUEST_SUCCESS_MSG, responseBody);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static ResultVO fail() {
        return new ResultVO(BaseExceptionEnum.SYSTEM_ERROR);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @param baseException
     * @return Result
     */
    public static ResultVO fail(DataintBaseException baseException) {
        return fail(baseException, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return ResultVO
     */
    public static ResultVO fail(DataintBaseException baseException, Object data) {
        return new ResultVO<>(baseException.getExceptionType(), data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param exceptionType
     * @param data
     * @return ResultVO
     */
    public static ResultVO fail(ExceptionType exceptionType, Object data) {
        return new ResultVO<>(exceptionType, data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param exceptionType
     * @return ResultVO
     */
    public static ResultVO fail(ExceptionType exceptionType) {
        return ResultVO.fail(exceptionType, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return ResultVO
     */
    public static ResultVO fail(Object data) {
        return new ResultVO<>(BaseExceptionEnum.SYSTEM_ERROR, data);
    }

}
