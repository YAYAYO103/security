package com.wangyu.mysecurity.controller;

import com.wangyu.mysecurity.comment.Result.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YAYAYO
 * @description: 测试
 * @date 2019.12.19 01915:29
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/a")
    public R test(){
        return R.ok("成功！");
    }
}
