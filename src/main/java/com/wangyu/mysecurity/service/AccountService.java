package com.wangyu.mysecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.entity.AccountEntity;

/**
 * <p>
 * 账户表 服务类
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-19
 */
public interface AccountService extends IService<AccountEntity> {

    R login(String username,String password);

    R logOut();

    R addAccount(AccountEntity accountEntity);

}
