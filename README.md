# Kafka Setup using Docker

## 1. Overview of Kafka

![Kafka Overview](https://github.com/digitalnus/kafka-docker/blob/main/images/Screenshot%202021-08-13%20at%205.27.35%20AM.png)

## 2. Install Apache Kafka

This section covers the Kafka installation procedures in Mac.

### 2.1 Mac Installation

#### 2.1.1 Check Java version
Ensure that Java 8 is installed in your machine by typing:

> *java -version*

You should see a similar output:

```
java version "1.8.0_231"
Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)
```

#### 2.1.2 Update brew
If Java is not installed or there is an issue on running Kafka, first perform an update of brew and then attempt to install Java 8 again.

> *brew tap homebrew/cask-versions*

You will see a bunch of outputs and after that, issue the next command to install java8

> *brew install java8*

#### 2.1.3 Install Kafka
Install Kafka using the brew install command:

> *brew install kafka*

Upon successful installation, Kafka will be installed at the following folder:

```
/usr/local/Cellar/kafka/2.8.0
```

Next, add the Kafka bin folder to the local path with the following command:

> *export PATH=$PATH:/usr/local/Cellar/kafka/2.8.0/bin*

#### 2.1.4 Test run Kafka
Run the following command to test the installation of Kafka

> *kafka-topics*

You should see a similar output as such:

```
Create, delete, describe, or change a topic.
Option                                   Description                            
------                                   -----------                            
--alter                                  Alter the number of partitions,        
                                           replica assignment, and/or           
                                           configuration for the topic.         
--at-min-isr-partitions                  if set when describing topics, only    
                                           show partitions whose isr count is   
                                           equal to the configured minimum. Not 
                                           supported with the --zookeeper       
                                           option.                              
--bootstrap-server <String: server to    REQUIRED: The Kafka server to connect  
  connect to>                              to. In case of providing this, a     
                                           direct Zookeeper connection won't be 
                                           required.                            
--command-config <String: command        Property file containing configs to be 
  config property file>                    passed to Admin Client. This is used 
                                           only with --bootstrap-server option  
                                           for describing and altering broker   
                                           configs.
```

## 3.0 Running Kafka

### 3.1 Starting Zookeeper

Assuming that Kafka bin directory is in your environment PATH, issue the following command to start Zookeeper

> *zookeeper-server-start config/zookeeper.properties*

You will see a similar output:

```
[2021-08-13 07:19:28,878] INFO Reading configuration from: config/zookeeper.properties (org.apache.zookeeper.server.quorum.QuorumPeerConfig)
[2021-08-13 07:19:28,882] WARN config/zookeeper.properties is relative. Prepend ./ to indicate that you're sure! (org.apache.zookeeper.server.quorum.QuorumPeerConfig)
[2021-08-13 07:19:28,893] INFO clientPortAddress is 0.0.0.0:2181 (org.apache.zookeeper.server.quorum.QuorumPeerConfig)
[2021-08-13 07:19:28,894] INFO secureClientPort is not set (org.apache.zookeeper.server.quorum.QuorumPeerConfig)
[2021-08-13 07:19:28,899] INFO autopurge.snapRetainCount set to 3 (org.apache.zookeeper.server.DatadirCleanupManager)
[2021-08-13 07:19:28,899] INFO autopurge.purgeInterval set to 0 (org.apache.zookeeper.server.DatadirCleanupManager)
[2021-08-13 07:19:28,899] INFO Purge task is not scheduled. (org.apache.zookeeper.server.DatadirCleanupManager)
[2021-08-13 07:19:28,899] WARN Either no config or no quorum defined in config, running  in standalone mode (org.apache.zookeeper.server.quorum.QuorumPeerMain)
:
:
:
[2021-08-13 07:19:29,153] INFO PrepRequestProcessor (sid:0) started, reconfigEnabled=false (org.apache.zookeeper.server.PrepRequestProcessor)
[2021-08-13 07:19:29,165] INFO Using checkIntervalMs=60000 maxPerMinute=10000 (org.apache.zookeeper.server.ContainerManager)
```



