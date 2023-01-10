package com.example.rabbitmqdemo.test_b;

import com.example.rabbitmqdemo.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecverB1 {
    private final static String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //轻量级的Connection
        Channel channel = connection.createChannel();
        //创建队列，创建完成后才能发送消息--防止消费者去找的时候生产者还没提供，导致报错
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //设置每个消费者同时只能处理一条信息
        channel.basicQos(1);
        //接收消息
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    String msg = new String(body);
                    System.out.println("[消费者1] received ： "+msg + "!");

                    //手动维护Ack
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }


}
