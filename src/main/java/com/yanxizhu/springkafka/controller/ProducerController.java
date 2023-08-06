package com.yanxizhu.springkafka.controller;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * 生产者的使用
 * 同步发送和异步发送的使用
 */
@RestController
public class ProducerController {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    /**
     * 生产者同步发送消息
     * @param message
     * @return
     */
    @GetMapping("/send/sync/{message}")
    public String sendBySync(@PathVariable String message) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("topic-springboot-kafka-01", 0, 0, message);
        //同步发送
        try {
            SendResult<Integer, String> result = future.get();
            RecordMetadata recordMetadata = result.getRecordMetadata();
            System.out.println("发送消息主题：" + recordMetadata.topic());
            System.out.println("发送消息分区：" + recordMetadata.partition());
            System.out.println("发送消息偏移量：" + recordMetadata.offset());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "success";
    }


    /**
     * 生产者异步发送消息
     * @param message
     * @return
     */
    @GetMapping("/send/async/{message}")
    public String sendByAsync(@PathVariable String message) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("topic-springboot-kafka-01", 0, 0, message);
        //设置异步回调，异步等待Kafka服务返回消息
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("异步消息发送失败");
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                System.out.println("发送消息主题：" + recordMetadata.topic());
                System.out.println("发送消息分区：" + recordMetadata.partition());
                System.out.println("发送消息偏移量：" + recordMetadata.offset());
                System.out.println("异步消息发送成功");
            }
        });
        return "success";
    }
}
