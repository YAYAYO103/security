package com.wangyu.mysecurity.bean.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author YAYAYO
 * @description: 登录
 * @date 2019.12.24 0249:40
 */
@Data
public class LoginForm {

    @NotBlank(message = "账户名不能为空！")
    private String account;

    @NotBlank(message = "密码不能为空！")
    private String password;
}
