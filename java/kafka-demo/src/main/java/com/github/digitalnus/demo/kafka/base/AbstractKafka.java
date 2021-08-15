package com.github.digitalnus.demo.kafka.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public abstract class AbstractKafka {
    protected Logger logger;
    protected Properties props = new Properties();

    public AbstractKafka() {
        logger = LoggerFactory.getLogger(getClass());
    }




}
