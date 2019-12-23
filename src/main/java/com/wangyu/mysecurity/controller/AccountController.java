package com.wangyu.mysecurity.controller;

import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.comment.validate.GroupOne;
import com.wangyu.mysecurity.entity.AccountEntity;
import com.wangyu.mysecurity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 新增账户
     * @param
     * @param
     * @return
     */
    @PostMapping("/addAccount")
    public R addAccount(@RequestBody @Validated AccountEntity entity){
        return accountService.addAccount(entity);
    }

    /**
     * 编辑账户信息
     * @param
     * @param
     * @return
     */
    @PostMapping("/updateAccount")
    public R updateAccount(@RequestBody @Validated AccountEntity entity){
        return accountService.updateAccount(entity);
    }

}
