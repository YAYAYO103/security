package com.wangyu.mysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
    private Integer aId;

    /**
     * 登录名
     */
    @NotBlank(message = "用户名不能为空！")
    @TableField("a_username")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空！")
    @TableField("a_password")
    private String password;


}
