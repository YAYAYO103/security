package com.wangyu.mysecurity.controller;

import com.wangyu.mysecurity.bean.request.LoginForm;
import com.wangyu.mysecurity.comment.Enums.Constants;
import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.comment.aop.Log;
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
     * @description: 登录
     * @author YAYAYO
     * @date 2019.12.24 024
     */
    @PostMapping("/login")
    @Log(value = "【#{#form.account}】用户登录",type = Constants.LogTypeEnum.SPEL)
    public R test(@RequestBody @Validated LoginForm form){
        return accountService.login(form);
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

    /**
     * @description: 账户解锁或者冻结
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    @Log(value = "{}用户将账号{}",type = Constants.LogTypeEnum.THREADLOCAL)
    @GetMapping("/lockAccount")
    public R lockAccount(Integer id){
        return accountService.lockAccount(id);
    }

}
