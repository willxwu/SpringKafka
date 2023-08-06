package com.yanxizhu.springkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消费者的使用
 */
@Component
public class ConsumerComment {


    @KafkaListener(topics = "topic-springboot-kafka-01")
    public void onMessage(ConsumerRecord<Integer, String> record) {
        System.out.println("消费消息主题：" + record.topic() +
                "消费消息分区：" + record.partition() + "\t" +
                "消费消息偏移量：" + record.offset() + "\t" +
                "消费消息KEY：" + record.key() + "\t" +
                "消费消息VALUE：" + record.value());
    }
}
