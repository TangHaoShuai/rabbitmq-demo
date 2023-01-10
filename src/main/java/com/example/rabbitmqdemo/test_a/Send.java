package com.example.rabbitmqdemo.test_a;

import com.example.rabbitmqdemo.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者发消息
 */
public class Send {
    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 轻量级的 Connection，这是完成大部分API的地方。
        Channel channel = connection.createChannel();

        // 声明（创建）队列，必须声明队列才能够发送消息，我们可以把消息发送到队列中。
        // 声明一个队列是幂等的 - 只有当它不存在时才会被创建 幂等性:多次调用方法或者接口不会改变业务状态
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消息内容
        String message = "日常生成BUG--尼古拉斯·唐-3";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        //关闭通道和连接
        channel.close();
        connection.close();
    }


}
