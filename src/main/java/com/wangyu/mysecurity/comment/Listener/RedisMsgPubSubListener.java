package com.wangyu.mysecurity.comment.Listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

/**
 * @author YAYAYO
 * @description: redis消息监听器
 * @date 2020.1.9 00917:08
 */
@Component
@Slf4j
public class RedisMsgPubSubListener extends AbstractMessageListener {

    @Value("${spring.redis.database}")
    private String database;

    public RedisMsgPubSubListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
    * @description: redis监听命令
    * @author YAYAYO
    * @date 2020.1.10 010
    */
    @Override
    protected Topic getTopic() {
        return new PatternTopic("__keyevent@"+database+"__:expired");
    }

    /**
    * @description: 监听到消息后处理事件
    * @author YAYAYO
    * @date 2020.1.9 009
    */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        // 注意：我们只能获取到失效的 key 而获取不到 value
        log.info("我是redis消息监听器，我已经收到消息了！【{}】已经过期",new String(message.getBody()));
        log.info("我是redis消息监听器，我已经收到消息了！【{}】",new String(bytes));
    }
}
