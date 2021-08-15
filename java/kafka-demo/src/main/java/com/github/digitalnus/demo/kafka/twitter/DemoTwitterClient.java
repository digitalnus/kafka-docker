package com.github.digitalnus.demo.kafka.twitter;


import com.github.digitalnus.demo.kafka.base.AbstractKafka;
import com.github.digitalnus.demo.kafka.base.MyProducerWithCallback;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * TwitterClient will consume tweets based on selected topic(s) and send to a KafkaProducer.
 *
 *
 */
public class DemoTwitterClient extends AbstractKafka {
    private static final String CONSUMER_KEY="TWITTER_API";
    private static final String CONSUMER_SECRET="TWITTER_SECRET";
    private static final String TOKEN="TWITTER_ACCESS_TOKEN";
    private static final String TOKEN_SECRET="TWITTER_TOKEN_SECRET";
    private static final String KAFKA_SERVER="KAFKA_SERVER";

    private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>(1000);
    private Client client;

    // Following terms - Empty list in the beginning
    List<String> terms = new ArrayList<>();

    public DemoTwitterClient() {
    }

    protected void cleanup() {
        // Nothing to cleanup here
        removeAllTerms();

        logger.info("Stopping client ...");
        if(client!=null) {
            client.stop();
        }
    }

    /**
     * Add a new term to the term list
     * @param termToFollow A string representing the term to follow
     * @return Total number of terms in the list after adding this new term
     */
    public int addTerms(String termToFollow) {
        terms.add(termToFollow);
        return terms.size();
    }

    public int count() {
        return terms.size();
    }

    public void removeAllTerms() {
        if(terms!=null) {
            terms.clear();
        }
    }

    private Client createTwitterClient(BlockingQueue msgQueue) {
        int cnt = count();
        logger.info("Total search terms = "+cnt);
        if(cnt == 0) {
            logger.info("No search term specified. Please add a term before running this client");
            return null;
        }

        // Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth)
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // set the terms to track
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(getEnv(CONSUMER_KEY), getEnv(CONSUMER_SECRET), getEnv(TOKEN), getEnv(TOKEN_SECRET));
        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));
        Client hosebirdClient = builder.build();
        return hosebirdClient;
    }

    private MyProducerWithCallback createKafkaProducer() {
        String kafkaServer = getEnv(KAFKA_SERVER);
        return new MyProducerWithCallback(kafkaServer);
    }


    public void run()  {
        logger.debug("Inside run() method ...");

        // Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(1000);

        // Create Twitter Client
        client = createTwitterClient(msgQueue);

        // Create a MyProducerWithCallback object that will allow posting of messages received from
        // Twitter tweets to be posted into the Kafka partitions
        MyProducerWithCallback producer = createKafkaProducer();

        // Attempts to establish a connection.
        client.connect();

        // on a different thread, or multiple different threads....
        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.info("Error when polling message queue!",e);
                client.stop();
            }

            if(msg!=null) {
                logger.info("Received message: "+msg);
                producer.sendMessage("twitter_tweets",msg);
            }
        } // while loop
        logger.info("End of application run");
    }


    public static void main(String[] args) {
        DemoTwitterClient client = new DemoTwitterClient();
        client.addTerms("kafka");
        client.run();
    }

}
