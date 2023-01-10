package com.example.rabbitmqdemo.testE;

import com.example.rabbitmqdemo.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者
 * Topic-主题模型
 * Topic类型的Exchange与Direct相比，都是可以根据RoutingKey把消息路由到不同的队列。只不过Topic类型Exchange可以让队列在绑定Routing key 的时候使用通配符！
 *
 * Routingkey 一般都是有一个或多个单词组成，多个单词之间以”.”分割，例如： item.insert
 *
 * 通配符规则：
 *
 * `#`：匹配一个或多个词
 *
 * `*`：匹配不多不少恰好1个词
 *
 * `audit.#`：能够匹配`audit.irs.corporate` 或者 `audit.irs`
 *
 * `audit.*`：只能匹配`audit.irs`

 */
public class SendE {
    private final static String EXCHANGE_NAME = "topic_exchange_test";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为topic
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 消息内容
        String message = "商品新增了， id = 1001";
        // 发送消息，并且指定routing key 为：insert ,代表新增商品
        channel.basicPublish(EXCHANGE_NAME, "item.insert", null, message.getBytes());
        System.out.println(" [商品服务：] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
