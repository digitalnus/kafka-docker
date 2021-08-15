package com.github.digitalnus.demo.kafka.twitter;


import com.github.digitalnus.demo.kafka.base.AbstractKafka;
import com.google.common.collect.Lists;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TwitterClient will consume tweets based on selected topic(s) and send to a KafkaProducer.
 *
 *
 */
public class DemoTwitterClient extends AbstractKafka {

    /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);
    private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>(1000);

    public DemoTwitterClient() {
        // Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth)
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        // Optional: set up some followings and track terms
        List<Long> followings = Lists.newArrayList(1234L, 566788L);
        List<String> terms = Lists.newArrayList("twitter", "api");
        hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1("consumerKey", "consumerSecret", "token", "secret");
    }

    protected void cleanup() {
        // Nothing to cleanup here
    }

    public void run() {
        logger.debug("Inside run() method ...");
    }

    public static void main(String[] args) {
        DemoTwitterClient client = new DemoTwitterClient();
        client.run();
    }

}
