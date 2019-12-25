package com.wangyu.mysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wangyu.mysecurity.comment.validate.GroupOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 账户表
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("account")
public class AccountEntity implements Serializable {

    private static final long serialVersionUID=12666L;

    /**
     * id
     */
    @TableId(value = "a_id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录名
     */
    @NotBlank(message = "用户名不能为空！")
    @TableField("a_account")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空！")
    @TableField("a_password")
    private String password;

    /**
     * 账号状态
     */
    @TableField("a_type")
    private boolean type;

    /**
     *账号级别（1超级管理员 2普通用户）
     */
    @TableField("a_level")
    private Integer level;


}
