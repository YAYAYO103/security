package com.wangyu.mysecurity.comment.config;

import com.wangyu.mysecurity.comment.Exception.RRException;
import com.wangyu.mysecurity.comment.Result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * @discription: 处理所有的RRException
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
    */
    @ExceptionHandler(RRException.class)
    public R RRExceptionHandler(Exception e){
        log.error("异常：【{}】",e.getMessage());
        e.printStackTrace();
        if(e instanceof RRException){
            RRException r= (RRException) e;
            return R.error(r.getCode(),r.getMessage());
        }
        return R.error(e.getMessage());
    }

    /**
     * 数据校验异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R MethodArgumentNotValidExceptionHandler(Exception e){
        log.error("校验异常：【{}】",e.getMessage());
        if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException r= (MethodArgumentNotValidException) e;
            return R.error(r.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
        return R.error(e.getMessage());
    }

    /**
     * @discription: 处理所有的Exception
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
     */
    @ExceptionHandler(Exception.class)
    public R ExceptionHandler(Exception e){
        log.error("异常：【{}】",e.getMessage());
        return R.error(e.getMessage());
    }
}
