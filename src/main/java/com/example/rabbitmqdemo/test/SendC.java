package com.example.rabbitmqdemo.test;

import com.example.rabbitmqdemo.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者
 * Fanout-广播模型
 * 在广播模式下，消息发送流程是这样的：
 *
 * 可以有多个消费者
 *
 * 每个消费者有自己的queue（队列）
 *
 * 每个队列都要绑定到Exchange（交换机）
 *
 * 生产者发送的消息，只能发送到交换机，交换机来决定要发给哪个队列，生产者无法决定。
 *
 *  交换机把消息发送给绑定过的所有队列
 *
 *  队列的消费者都能拿到消息。实现一条消息被多个消费者消费
 */
public class SendC {
    private final static String EXCHANGE_NAME = "fanout_exchange_test";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();

        // 声明exchange，指定类型为fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 消息内容
        String message = "Hello everyone3";
        // 发布消息到Exchange
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [生产者] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
