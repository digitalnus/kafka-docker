package com.github.digitalnus.demo.kafka.base;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;



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
public class MyProducerWithCallback extends AbstractKafka {

    private KafkaProducer <String,String> producer;

    public MyProducerWithCallback(String bootstrapServer) {
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        producer = new KafkaProducer<>(props);
    }



    public void sendMessage(String topic, String key, String message) {
        // Creating the message
        ProducerRecord <String, String> record;
        if(key!=null) {
            record = new ProducerRecord<>(topic,key, message);
        } else {
            record = new ProducerRecord<>(topic,message);
        }

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
    }

    public void sendMessage(String topic, String message) {
        sendMessage(topic,null,message);
    }

    protected void cleanup() {
        if(producer!=null)
        {
            // Closing the stream
            producer.close();
        }
    }


    public static void main(String[] args) {
        String server = "127.0.0.1:9092";
        MyProducerWithCallback myproducer = new MyProducerWithCallback(server);
        for (int i=0; i<10; i++) {
            myproducer.sendMessage("first_topic", "This is message #" + i);
        }
    }
}
