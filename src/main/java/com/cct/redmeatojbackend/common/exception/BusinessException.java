package com.cct.redmeatojbackend.common.exception;


import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;

/**
 * 自定义异常类
 * @author cct
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(RespCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(RespCodeEnum errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
