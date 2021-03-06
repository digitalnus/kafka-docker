package com.github.digitalnus.demo.kafka.base;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.List;

/**
 * MyConsumer - Receives message from the Kafka server.
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
public class MyConsumer extends AbstractKafka {

    private KafkaConsumer<String,String> consumer;

    MyConsumer(String bootstrapServer, String groupId, List <String> topicList) {
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        // Create the consumer
        consumer = new KafkaConsumer<>(props);

        // Subscribe to a list of topic(s)
        consumer.subscribe(topicList);
    }

    public void listen() {
        // Listen for messages from the producer
        logger.debug("Listening for new messages ... ");
    }

    protected void cleanup() {
        if(consumer!=null) {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        String server = "127.0.0.1:9092";
        String groupId = "my-demo-group";
        List <String> topicList = Arrays.asList("first_topic");

        MyConsumer myconsumer = new MyConsumer(server,groupId, topicList);
        myconsumer.listen();
    }
}
