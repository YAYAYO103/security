package com.wangyu.mysecurity.comment.Exception;

import com.wangyu.mysecurity.comment.Enums.ResultEnum;
import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class RRException extends RuntimeException {
    private int code;

    public RRException(String msg) {
        super(msg);
    }

    public RRException(int code,String msg) {
        super(msg);
        this.code = code;
    }

    public RRException(ResultEnum.ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code=exceptionEnum.getCode();
    }
}
