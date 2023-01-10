package com.example.rabbitmqdemo.test_d;

import com.example.rabbitmqdemo.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者
 * 有选择性的接收消息
 *
 * 在订阅模式中，生产者发布消息，所有消费者都可以获取所有消息。
 *
 * 在路由模式中，我们将添加一个功能 - 我们将只能订阅一部分消息。 例如，我们只能将重要的错误消息引导到日志文件（以节省磁盘空间），同时仍然能够在控制台上打印所有日志消息。
 *
 * 但是，在某些场景下，我们希望不同的消息被不同的队列消费。这时就要用到Direct类型的Exchange。
 *
 * 在Direct模型下，队列与交换机的绑定，不能是任意绑定了，而是要指定一个RoutingKey（路由key）
 *
 * 消息的发送方在向Exchange发送消息时，也必须指定消息的routing key
 *
 * P：生产者，向Exchange发送消息，发送消息时，会指定一个routing key。
 *
 * X：Exchange（交换机），接收生产者的消息，然后把消息递交给 与routing key完全匹配的队列
 *
 * C1：消费者，其所在队列指定了需要routing key 为 error 的消息
 *
 * C2：消费者，其所在队列指定了需要routing key 为 info、error、warning 的消息
 *
 */
public class SendD {
    private final static String EXCHANGE_NAME = "direct_exchange_test";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 消息内容
        String message = "商品新增了， id = 1001";
        // 发送消息，并且指定routing key 为：insert ,代表新增商品
        channel.basicPublish(EXCHANGE_NAME, "insert", null, message.getBytes());
        System.out.println(" [商品服务：] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
