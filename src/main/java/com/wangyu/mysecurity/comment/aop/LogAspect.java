package com.wangyu.mysecurity.comment.aop;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wangyu.mysecurity.comment.Enums.Constants;
import com.wangyu.mysecurity.comment.Enums.ResultEnum;
import com.wangyu.mysecurity.comment.Exception.RRException;
import com.wangyu.mysecurity.comment.Local;
import com.wangyu.mysecurity.comment.Result.R;
import com.wangyu.mysecurity.comment.utils.CommentUtils;
import com.wangyu.mysecurity.comment.utils.RedisUtil;
import com.wangyu.mysecurity.entity.AccountEntity;
import com.wangyu.mysecurity.entity.LogEntity;
import com.wangyu.mysecurity.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author YAYAYO
 * @description: 日志aop
 * @date 2019.12.25 02511:33
 */
@Component
@Aspect
@Slf4j
public class LogAspect{

    @Autowired
    private LogMapper logMapper;

    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * @description: 解析spel表达式
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    private String generateKeyBySpEL(String spELString, ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        Expression expression = parser.parseExpression(spELString,new TemplateParserContext());
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        for(int i = 0 ; i < args.length ; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return expression.getValue(context,String.class);
    }

    /**
     * @description: 字符串解析表达式
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    private String generateKeyByLocal(String str,String[] parameters){
        if(StringUtils.isEmpty(str) || parameters.length<1){
            return "";
        }

        StringBuilder sb=new StringBuilder();
        char[] value = str.toCharArray();
        Integer count=0;
        for (int i = 0; i < value.length; i++) {

            if(count>=parameters.length){
                sb.append(value[i]);
                continue;
            }

            if('{'==value[i] && '}'==value[i+1]){
                sb.append(parameters[count]);
                count++;
                i++;
                continue;
            }

            sb.append(value[i]);
        }
        return sb.toString();
    }

    /**
     * @description: 定义切点
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    @Pointcut("@annotation(com.wangyu.mysecurity.comment.aop.Log)")
    public void logPointCut() {
    }

    /**
     * @description: 环绕通知
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //先执行方法
        Object proceed = joinPoint.proceed();
        //如果没有抛异常，开始记录日志
        log.info("日志操作：开始记录日志......");

        //获取目标方法的基本信息
        Object[] parameter = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Log logger = method.getAnnotation(Log.class);

        //没有log注解
        if(logger==null){
            return proceed;
        }

        //有  就记录日志
        String value = logger.value();

        Constants.LogTypeEnum type = logger.type();
        if(Constants.LogTypeEnum.NORMAL==type){
            //直接记录数据库 无需处理

        }else if(Constants.LogTypeEnum.SPEL==type){
            //spel表达式处理
            value= this.generateKeyBySpEL(value, joinPoint);

        }else if(Constants.LogTypeEnum.THREADLOCAL==type){
            //ThreadLocal处理
            String[] parameters = Local.getThreadLocal().get();
            value=this.generateKeyByLocal(value,parameters);
            Local.clear();

        }

        //记录日志
        LogEntity entity=new LogEntity();
        entity.setCreateTime(new Date())
                .setLAccount(this.getAccountId(proceed))
                .setLDescription(value)
                .setLIp(CommentUtils.getIpAddr());

        logMapper.insert(entity);

        log.info("日志操作，记录日志完成......");

        return proceed;
    }

    /**
     * @description: 获取当前用户的id
     * @author YAYAYO
     * @date 2019.12.25 025
     */
    private static Integer getAccountId(Object proceed){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token=request.getParameter("token");
        }
        if(StringUtils.isEmpty(token)){
            if(proceed instanceof R) {
                R r = (R) proceed;
                Object data = r.getData();
                JSONObject jsonObject = JSONUtil.parseObj(data);
                token = jsonObject.get("token").toString();
            }else {
                //异常情况
                return null;
            }
        }

        String accountStr = RedisUtil.get(token+ Constants.REDIS_ACCOUNT_SUFFIX);
        if(StringUtils.isEmpty(accountStr)){
            throw new RRException(ResultEnum.ExceptionEnum.NOT_LOGIN);
        }
        AccountEntity accountEntity = JSONUtil.toBean(accountStr, AccountEntity.class);
        return accountEntity.getId();
    }

}
