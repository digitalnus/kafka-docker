package com.github.digitalnus.demo.kafka.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class MyProducerWithCallback {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(MyProducerWithCallback.class);

        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        KafkaProducer <String,String> producer = new KafkaProducer<>(props);

        String topic = "first_topic";
        String message = "This is message #";
        for(int i=0; i<10; i++) {
            // Creating the message
            ProducerRecord <String, String> record = new ProducerRecord<>(topic,message+Integer.toString(i));

            // Sending the message
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        // data was sent successfully
                        logger.info("Data sent successfully. \n"+
                                "Topic    : "+recordMetadata.topic()+"\n"+
                                "Partition: "+recordMetadata.partition()+"\n"+
                                "Offset   : "+recordMetadata.offset()+"\n"+
                                "Timestamp: "+recordMetadata.timestamp()+"\n");
                    } else {
                        // there was an exception when sending the message, deal with it
                        logger.error(e.getLocalizedMessage());
                    }
                }
            });

            // Async , need to flush
            producer.flush();
        } // For loop

        // Closing the stream
        producer.close();
    }
}
