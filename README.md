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

Open another new command window and verify that Zookeeper is writing data to the /tmp/zookeeper folder (as specified in the zookeeper.properties)

> *ls -l /tmp/zookeeper*

You should see a similar output:

```
drwxr-xr-x  3 user  wheel  96 Aug 13 07:19 version-2
```

### 3.2 Starting Kafka server

Next we are going to start Kafka server by issuing the following command:

> *kafka-server-start config/server.properties*

You should see a similar output:

```
[2021-08-13 07:31:05,367] INFO Registered kafka:type=kafka.Log4jController MBean (kafka.utils.Log4jControllerRegistration$)
[2021-08-13 07:31:06,012] INFO Setting -D jdk.tls.rejectClientInitiatedRenegotiation=true to disable client-initiated TLS renegotiation (org.apache.zookeeper.common.X509Util)
[2021-08-13 07:31:06,190] INFO Registered signal handlers for TERM, INT, HUP (org.apache.kafka.common.utils.LoggingSignalHandler)
[2021-08-13 07:31:06,197] INFO starting (kafka.server.KafkaServer)
[2021-08-13 07:31:06,198] INFO Connecting to zookeeper on localhost:2181 (kafka.server.KafkaServer)
[2021-08-13 07:31:06,237] INFO [ZooKeeperClient Kafka server] Initializing a new session to localhost:2181. (kafka.zookeeper.ZooKeeperClient)
[2021-08-13 07:31:06,251] INFO Client environment:zookeeper.version=3.5.9-83df9301aa5c2a5d284a9940177808c01bc35cef, built on 01/06/2021 20:03 GMT (org.apache.zookeeper.ZooKeeper)
[2021-08-13 07:31:06,251] INFO Client environment:host.name=localhost (org.apache.zookeeper.ZooKeeper)
:
:
[2021-08-13 07:31:09,665] INFO Kafka commitId: ebb1d6e21cc92130 (org.apache.kafka.common.utils.AppInfoParser)
[2021-08-13 07:31:09,666] INFO Kafka startTimeMs: 1628854269641 (org.apache.kafka.common.utils.AppInfoParser)
[2021-08-13 07:31:09,667] INFO [KafkaServer id=0] started (kafka.server.KafkaServer)
[2021-08-13 07:31:09,697] INFO [broker-0-to-controller-send-thread]: Recorded new controller, from now on will use broker localhost:9092 (id: 0 rack: null) (kafka.server.BrokerToControllerRequestThread)
```

### 3.3 Creating the first topic

Create the first topic with topic name = demo_topic and start with 3 partitions and only 1 replication factor (as there is only 1 broker up at this moment)

> *kafka-topics --bootstrap-server localhost:9092 --topic demo_topic --create --partitions 3 --replication-factor 1*

You should see the following output if the topic is created successfully:

```
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic demo_topic.
```

### 3.4 List all topics

After creating topics, verify that it is running by issuing the command:

> *kafka-topics --bootstrap-server localhost:9092 --list*

You should see similar output:

```
demo2_topic
demo_topic
```

#### 3.4.1 List a specific topic

> *kafka-topics --bootstrap-server localhost:9092 --topic demo_topic --describe*

This will show in detail the configuration setup of the topic such as partitions, replication-factor etc

```
Topic: demo2_topic	TopicId: qUguWcruRUCms6NEF95yPA	PartitionCount: 3	ReplicationFactor: 1	Configs: segment.bytes=1073741824
	Topic: demo2_topic	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
	Topic: demo2_topic	Partition: 1	Leader: 0	Replicas: 0	Isr: 0
	Topic: demo2_topic	Partition: 2	Leader: 0	Replicas: 0	Isr: 0
```

### 3.5 Producing topic messages

#### 3.5.1 Producing via console

> *kafka-console-producer --broker-list localhost:9092 --topic demo_topic*

You can start entering the message to put into the demo_topic, press ctrl-c once you have completed the messaging

```
>This is the first demo message
>This is the second
>This is the third
>Press ctrl-c to complete the messaging after this line
>^C
```

#### 3.5.2 Producer with key-value parsing
To enable key-value pairs parsing capabilities for the producer, use the --property with parse.key=true and specifying the separator using key.separator as such:

> *kafka-console-producer --broker-list 127.0.0.1:9092 --topic demo_topic --property parse.key=true --property key.separator=,*


### 3.6 Consuming topic messages

#### 3.6.1 Consuming topic from the beginning
To consume topic messages from the beginning, append it with the --from-beginning flag. If this is not specified, console consumer will be waiting for the next topic message to be inserted and display the message only.

> *kafka-console-consumer --bootstrap-server localhost:9092 --topic demo_topic --from-beginning*

#### 3.6.2 Specifying a group option

Append the --group [group name] to include a consume group.

Group allows consumers to round robin and read the messages coming from a producer.

For example, producer added "Message 1", "Message 2" and "Message 3" to the queue, if there are 3 consumers in the same group, then each of the consumer will get one of the 3 messages. Groups allow load to be shared amont consumers in a group.	

Note: Once --group is specified, the consumer_offset will be committed, meaning that all previous messages that were read will not appear again if the same command is run again.

> *kafka-console-consumer --bootstrap-server localhost:9092 --topic demo_topic --group demo_group*

	
#### 3.6.3 Consuming key-value pair messages

You can consume messages using key-value pairs as such:
	
> *kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning --property print.key=true --property key.separator=,*
	
## 4.0 

## References

a) Java JDK 8 Download: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

b) IntelliJ Community Edition Download: https://www.jetbrains.com/idea/download/

c) Kafka Clients: https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients

d) Kafka API Documentation: https://kafka.apache.org/documentation/

e) Twitter API

Twitter Java Client    : https://github.com/twitter/hbc

Twitter API Credentials: https://developer.twitter.com/

f) Elastic Search

Elastic Search Java Client  : 

https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.4/java-rest-high.html or
https://bonsai.io/


