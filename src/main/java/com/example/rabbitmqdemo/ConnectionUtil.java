package com.example.rabbitmqdemo;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

/**
 * 连接MQ工具类
 */
public class ConnectionUtil {
    /**
     * 建立与RabbitMQ的连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("192.168.148.104");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
