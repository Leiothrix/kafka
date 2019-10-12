package com.paprika.kafka.producer;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

/**
 * @author adam
 * @date 2019/10/12
 */
@Slf4j
public class Producer {

    private static final String TOPIC = "test";
    private static final String BROKER_LIST = "127.0.0.1:9092";
    private static KafkaProducer<String, String> producer;

    static {
        Properties configs = initConfig();
        producer = new KafkaProducer<>(configs);
    }

    /**
     * 初始化配置
     */
    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    public static void main(String... args) {
        // 消息实体
        ProducerRecord<String, String> record;
        int times = 1000;
        for (int i = 0; i < times; i++) {
            record = new ProducerRecord<>(TOPIC, "这是第" + (i + 1) + "条消息");
            // 发送消息
            producer.send(record, (recordMetadata, e) -> {
                if (null != e) {
                    log.info("send error:{}", e.getMessage());
                } else {
                    log.info("offset:{}, partition:{}", recordMetadata.offset(), recordMetadata.partition());
                }
            });
        }
        producer.close();
    }
}