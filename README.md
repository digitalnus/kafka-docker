# Kafka Setup using Docker

## 1. Overview of Kafka

![Kafka Overview](https://github.com/digitalnus/kafka-docker/blob/main/Screenshot%202021-08-13%20at%205.27.35%20AM.png)

## 2. Install Apache Kafka

Navigate to https://kafka.apache.org/downloads and download the latest version of Kafka

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




