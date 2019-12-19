package com.wangyu.mysecurity.controller;

import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YAYAYO
 * @description: 账户
 * @date 2019.12.19 01916:43
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 登录
     * @return
     */
    @GetMapping("/login")
    public R test(String username, String password){
        return accountService.login(username,password);
    }

    /**
     * 注销
     * @param
     * @param
     * @return
     */
    @GetMapping("/logOut")
    public R logOut(){
        return accountService.logOut();
    }

}
