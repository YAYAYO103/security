package com.wangyu.mysecurity.comment.lnterceptor;

import cn.hutool.json.JSONUtil;
import com.wangyu.mysecurity.comment.Enums.Constants;
import com.wangyu.mysecurity.comment.Enums.ResultEnum;
import com.wangyu.mysecurity.comment.Exception.RRException;
import com.wangyu.mysecurity.comment.aop.Anonymous;
import com.wangyu.mysecurity.comment.utils.CommentUtils;
import com.wangyu.mysecurity.comment.utils.RedisUtil;
import com.wangyu.mysecurity.entity.AccountEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author YAYAYO
 * @description: 全局权限拦截器
 * @date 2019.12.19 01916:08
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否有Anonymous注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Anonymous authority = method.getAnnotation(Anonymous.class);
        if (authority != null) {
            // 如果注解不为null, 说明不需要拦截, 直接放过
            return true;
        }

        CommentUtils.getAccount();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
