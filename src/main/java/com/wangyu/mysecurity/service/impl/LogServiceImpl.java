package com.wangyu.mysecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangyu.mysecurity.entity.LogEntity;
import com.wangyu.mysecurity.mapper.LogMapper;
import com.wangyu.mysecurity.service.LogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author YAYAYO
 * @since 2019-12-25
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements LogService {

}
