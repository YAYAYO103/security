package com.wangyu.mysecurity.comment.Result;

import com.wangyu.mysecurity.comment.Enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 全局返回值
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class R<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    private Long total;
    
    /**
     * @discription: 正常返回
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
    */
    public static <T> R<T> ok(String msg,T data,Long total){
        return new R<>(ResultEnum.OK.getCode(),msg,data,total);
    }

    /**
     * @discription: 正常返回
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
     */
    public static <T> R<T> ok(String msg,T data){
        return R.ok(msg,data,0L);
    }

    /**
     * @discription: 正常返回
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
     */
    public static <T> R<T> ok(String msg){
        return R.ok(msg,null);
    }

    /**
     * @discription: 正常返回
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
     */
    public static <T> R<T> ok(T data){
        return R.ok(ResultEnum.OK.getMsg(),data);
    }

    /**
     * @discription: 正常返回
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
     */
    public static <T> R<T> ok(){
        return R.ok((T)null);
    }

    /**
     * @discription: 失败时返回
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
     */
    public static <T> R<T> error(int code,String msg){
        return new R<>(code,msg,null,0L);
    }

    /**
     * @discription: 失败时返回
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
     */
    public static <T> R<T> error(String msg){
        return R.error(ResultEnum.ERROR.getCode(),msg);
    }

    /**
     * @discription: 失败时返回
     * @param:
     * @author: YAYAYO
     * @date: 2019.11.28 028
     */
    public static <T> R<T> error(){
        return R.error(ResultEnum.ERROR.getMsg());
    }
}
