package com.github.digitalnus.demo.kafka.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * MyProducer - Send message to the Kafka server.
 * Before running this code, open up 3 terminals and start the zookeeper, kafka server and kafka-console-consumer
 * for this code to work.
 *
 * 1) Starting zookeeper
 * zookeeper-server-start config/zookeeper.properties
 *
 * 2) Starting kafka-server
 * kafka-server-start config/server.properties
 *
 * 3) Starting kafka-console-consumer
 * Replace the group name and topic with the appropriate values
 *
 * kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group demo_app
 *
 */
public class MyProducer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        KafkaProducer <String,String> producer = new KafkaProducer<>(props);

        ProducerRecord <String, String> record = new ProducerRecord<>("first_topic","2nd message");

        producer.send(record);

        // Async , need to flush
        producer.flush();
        producer.close();

    }
}
