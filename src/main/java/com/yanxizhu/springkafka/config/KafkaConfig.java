package com.yanxizhu.springkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

/**
 * kafka内置对象个性化参数配置（可选配置）
 */
@Configurable
public class KafkaConfig {

    /**
     * 使用内置的KafkaAdmin对象自动帮我们创建SpringBootKafka-topic-1、SpringBootKafka-topic-1主题
     *
     * @return
     */
    @Bean
    public NewTopic topic1() {
        return new NewTopic("topic-SpringBoot-1", 3, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("topic-SpringBoot-2", 5, (short) 1);
    }

    /**
     * 覆盖原有的内置KafkaAdmin
     * 修改一个默认的参数属性
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        HashMap<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "192.168.56.30:9092");
        KafkaAdmin kafkaAdmin = new KafkaAdmin(configs);
        return kafkaAdmin;
    }

    /**
     * 修改原有kafkaTemplate属性参数
     * @param producerFactory
     * @return
     */
    @Bean
    @Autowired
    public KafkaTemplate<Integer, String> kafkaTemplate(ProducerFactory<Integer, String> producerFactory) {
        //覆盖ProducerFactory原有参数配置
        HashMap<String, Object> configsOverride = new HashMap<>();
        configsOverride.put(ProducerConfig.BATCH_SIZE_CONFIG, 200);

        KafkaTemplate<Integer, String> kafkaTemplate = new KafkaTemplate<>(
                producerFactory,
                configsOverride
        );
        return kafkaTemplate;
    }
}
