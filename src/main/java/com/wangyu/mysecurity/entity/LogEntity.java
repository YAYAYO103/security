package com.wangyu.mysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("log")
public class LogEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "l_id", type = IdType.AUTO)
    private Integer lId;

    /**
     * 账户id
     */
    private Integer lAccount;

    /**
     * 操作ip
     */
    private String lIp;

    /**
     * 操作描述
     */
    private String lDescription;

    private Date createTime;


}
