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

import java.util.ArrayList;
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

    // Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth)
    Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
    StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

    // Following terms - Empty list in the beginning
    List<String> terms = new ArrayList<>();

    // Following people in Twitter, not used, comment off
//        List<Long> followings = Lists.newArrayList(1234L, 566788L);
//        hosebirdEndpoint.followings(followings);

    public DemoTwitterClient() {
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1("consumerKey", "consumerSecret", "token", "secret");
    }

    protected void cleanup() {
        // Nothing to cleanup here
        removeAllTerms();
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


    public void run() {
        logger.debug("Inside run() method ...");
        int cnt = count();
        logger.info("Total search terms = "+cnt);
        if(cnt == 0) {
            logger.info("No search term specified. Please add a term before running this client");
            return;
        }
    }

    public static void main(String[] args) {
        DemoTwitterClient client = new DemoTwitterClient();
        client.addTerms("kafka");
        client.run();
    }

}
