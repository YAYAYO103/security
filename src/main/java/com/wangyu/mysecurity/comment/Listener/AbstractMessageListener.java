package com.wangyu.mysecurity.comment.Listener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

/**
 * @author YAYAYO
 * @description: redis消息监听抽象类
 * @date 2020.1.9 00917:50
 */
public abstract  class AbstractMessageListener implements MessageListener, InitializingBean {

    private final RedisMessageListenerContainer listenerContainer;

    public AbstractMessageListener(RedisMessageListenerContainer listenerContainer) {
        this.listenerContainer = listenerContainer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        listenerContainer.addMessageListener(this, getTopic());
    }

    protected abstract Topic getTopic();
}
