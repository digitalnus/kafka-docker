package com.github.digitalnus.demo.kafka.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public abstract class AbstractKafka {
    private String ENVFILE = "/Users/user/Desktop/Google Drive/Non-Sync/Development/kafka-docker/java/kafka-demo/src/main/java/keys.env";
    protected Logger logger;
    protected Properties props = new Properties();
    private Properties env = new Properties();

    public AbstractKafka() {
        logger = LoggerFactory.getLogger(getClass());
        loadEnv();

        // Perform any clean up actions when program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cleanup();
        }));
    }

    // Subclass need to implement this cleanup method to cleanup or close any connection upon
    // exiting the application
    protected abstract void cleanup();

    private void loadEnv() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(ENVFILE);
            InputStreamReader isr = new InputStreamReader(fis);
            env.load(isr);
            logger.info("Loaded "+ENVFILE+" with "+env.size()+" properties");
        } catch (Exception e) {
            logger.error("Error while reading keys.env file!",e);
        } finally {
            if(fis!=null) {
                try {
                    fis.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    protected final String getEnv(String key) {
        if(key==null || key.trim().length()==0) return "";
        return (String) env.get(key);
    }
}
