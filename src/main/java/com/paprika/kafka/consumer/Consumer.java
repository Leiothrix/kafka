package com.paprika.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author adam
 * @date 2019/10/12
 */
@Slf4j
public class Consumer {

    private static final String TOPIC = "test";
    private static final String BROKER_LIST = "127.0.0.1:9092";
    private static KafkaConsumer<String, String> consumer;

    static {
        Properties configs = initConfig();
        consumer = new KafkaConsumer<>(configs);
    }

    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", BROKER_LIST);
        properties.put("group.id", "0");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.offset.reset", "earliest");
        return properties;
    }

    public static void main(String[] args) {
        consumer.subscribe(Collections.singletonList(TOPIC));
        for (; ; ) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(0, 100000000));
            for (ConsumerRecord<String, String> record : records) {
                log.info(record.toString());
            }
        }
    }
}
