---
Reference: https://data-flair.training/blogs/kafka-architecture/
---

### Message broker
#### Comparison with other msg brokers
* Kafka 
    - Pros
        1. Integrate well with other components in big data processing and stream processing ecosystem
        2. Super high performance in async send/write. The extreme processing roughly 20 million msg / seconds
    - Cons
        1. A little low performance in sync send/write due to the batch upgrade inside. 
* RabbitMQ
    - Pros 
        1. Lightweight
        2. There is an exchange module between producer and queue which enables flexible routing.
    - Cons
        1. Written in Erlang, steep language learning curve.
        2. Relative low performance, 10K to 100K processed msg / second.
        3. Performance downgrade when large amounts of message accumulated and unprocessed.  
* ActiveMQ: Only choice 10 years ago
* ZeroMQ: Multi-threading network library

Kafka Architecture -- Apache Kafka APIs
--------------------------------------

Apache Kafka Architecture has four core APIs, producer API, Consumer API, Streams API, and Connector API. Let's discuss them one by one:

[![Kafka Architecture - Apache Kafka APIs](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/04/Apache-Kafka-Cluster-1.png)](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/04/Apache-Kafka-Cluster-1.png)

Kafka Architecture -- Apache Kafka APIs

### a. Producer API

In order to publish a stream of records to one or more Kafka topics, the Producer API allows an application. 

### b. Consumer API

This API permits an application to subscribe to one or more topics and also to process the stream of records produced to them.

### c. Streams API

Moreover, to act as a stream processor, consuming an input stream from one or more topics and producing an output stream to one or more output topics, effectively transforming the input streams to output streams, the streams API permits an application.

### d. Connector API

While it comes to building and running reusable producers or consumers that connect Kafka topics to existing applications or data systems, we use the Connector API. For example, a connector to a relational database might capture every change to a table.

Apache Kafka Architecture -- Cluster
-----------------------------------

The below diagram shows the cluster diagram of Apache Kafka:

[![Kafka Architecture](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/04/Kafka-Architecture.png)](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/04/Kafka-Architecture.png)

Kafka Architecture -- Kafka Cluster

Let's describe each component of Kafka Architecture shown in the above diagram:

### a. Kafka Broker

Basically, to maintain load balance [Kafka cluster ](https://data-flair.training/blogs/kafka-cluster/)typically consists of multiple brokers. However, these are stateless, hence for maintaining the cluster state they use ZooKeeper. Although, one Kafka Broker instance can handle hundreds of thousands of reads and writes per second. Whereas, without performance impact, each broker can handle TB of messages. In addition, make sure ZooKeeper performs Kafka broker leader election.

### b. Kafka -- ZooKeeper

For the purpose of managing and coordinating, Kafka broker uses ZooKeeper. Also, uses it to notify producer and consumer about the presence of any new broker in the Kafka system or failure of the broker in the Kafka system. As soon as Zookeeper send the notification regarding presence or failure of the broker then producer and consumer, take the decision and starts coordinating their task with some other broker.

### c. Kafka Producers

Further, Producers in Kafka push data to brokers. Also, all the producers search it and automatically sends a message to that new broker, exactly when the new broker starts. However, keep in mind that the <mark>Kafka producer sends messages as fast as the broker can handle, it doesn't wait for acknowledgments from the broker.</mark>

### d. Kafka Consumers

Basically, by using partition offset the [Kafka Consumer](https://data-flair.training/blogs/kafka-consumer/) maintains that how many messages have been consumed because Kafka brokers are stateless. Moreover, you can assure that the consumer has consumed all prior messages once the consumer acknowledges a particular message offset. Also, in order to have a buffer of bytes ready to consume, the consumer issues an asynchronous pull request to the broker. Then simply by supplying an offset value, consumers can rewind or skip to any point in a partition. In addition, ZooKeeper notifies Consumer offset value.

Kafka Architecture -- Fundamental Concepts
-----------------------------------------

Here, we are listing some of the fundamental concepts of Kafka Architecture that you must know:

### a. Kafka Topics

The topic is a logical channel to which producers publish message and from which the consumers receive messages.

1.  A topic defines the stream of a particular type/classification of data, in Kafka.
2.  Moreover, here messages are structured or organized. A particular type of messages is published on a particular topic.
3.  Basically, at first, a producer writes its messages to the topics. Then consumers read those messages from topics.
4.  In a Kafka cluster, a topic is identified by its name and must be unique.
5.  There can be any number of topics, there is no limitation.
6.  We can not change or update data, as soon as it gets published.

Below is the image which shows the relationship between Kafka Topics and Partitions:

[![Kafka Architecture](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/04/Kafka-topics-and-partitions-relationship.png)](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/04/Kafka-topics-and-partitions-relationship.png)

Kafka Architecture -- Relation between Kafka Topics and Partitions

### b. Partitions in Kafka

In a Kafka cluster, Topics are split into Partitions and also replicated across brokers.

1.  However, to which partition a published message will be written, there is no guarantee about that.
2.  Also, we can add a key to a message. Basically, we will get ensured that all these messages (with the same key) will end up in the same partition if a producer publishes a message with a key. Due to this feature, <mark>Kafka offers message sequencing guarantee.</mark> Though, unless a key is added to it, data is written to partitions randomly.
3.  Moreover, in one partition, messages are stored in the sequenced fashion.
4.  In a partition, each message is assigned an incremental id, also called offset.
5.  However, only within the partition, these offsets are meaningful. Moreover, in a topic, it does not have any value across partitions.
6.  There can be any number of Partitions, there is no limitation.

### c. Topic Replication Factor in Kafka

While designing a Kafka system, it's always a wise decision to factor in topic replication. As a result, its topics' replicas from another broker can solve the crisis, if a broker goes down. For example, we have 3 brokers and 3 topics. Broker1 has Topic 1 and Partition 0, its replica is in Broker2, so on and so forth. It has got a replication factor of 2; it means it will have one additional copy other than the primary one. Below is the image of Topic Replication Factor:

Don't forget to check -- [ Apache Kafka Streams Tutorial](https://data-flair.training/blogs/kafka-streams/)

[![Kafka Architecture](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/04/kafka-topic-replication.png)](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/04/kafka-topic-replication.png)

Kafka Architecture -- Topic Replication Factor

Some key points --

1.  Replication takes place in the partition level only.
2.  For a given partition, only one broker can be a leader, at a time. Meanwhile, other brokers will have in-sync replica; what we call ISR.
3.  It is not possible to have the number of replication factor more than the number of available brokers.

### d. Consumer Group

1.  It can have multiple consumer process/instance running.
2.  Basically, one consumer group will have one unique group-id.
3.  Moreover, exactly one consumer instance reads the data from one partition in one consumer group, at the time of reading.
4.  Since, there is more than one consumer group, in that case, one instance from each of these groups can read from one single partition.
5.  However, there will be some inactive consumers, if the number of consumers exceeds the number of partitions. Let's understand it with an example if there are 8 consumers and 6 partitions in a single consumer group, that means there will be 2 inactive consumers.


## Zookeper in Kafkaa

1\. What is ZooKeeper?
----------------------

Apache ZooKeeper plays the very important role in system architecture as it works in the shadow of more exposed [Big Data](https://data-flair.training/blogs/what-is-big-data/) tools, as [Apache Spark](https://data-flair.training/blogs/apache-spark-for-beginners/) or Apache Kafka. In other words, Apache Zookeeper is a distributed, open-source configuration, synchronization service along with naming registry for distributed applications. 

[![What is ZooKeeper](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/05/2018-05-11.png)](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/05/2018-05-11.png)

Now, let's discuss the role of ZooKeeper in Kafka in detail:

3\. ZooKeeper in Kafka 
-----------------------

Basically, Kafka -- ZooKeeper stores a lot of shared information about [Kafka Consumers ](https://data-flair.training/blogs/kafka-consumer/)and Kafka [Brokers](https://data-flair.training/blogs/kafka-broker/), let's discuss them in detail:

[![Requirement of ZooKeeper in Kafka](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/05/Role-of-ZooKeeper-in-Kafka-01.jpg)](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/05/Role-of-ZooKeeper-in-Kafka-01.jpg)

The requirement of ZooKeeper in Kafka

### a. Kafka Brokers

Below given are the roles of ZooKeeper in [Kafka Broker](https://data-flair.training/blogs/kafka-broker/):\
1. State\
Zookeeper determines the state. That means, it notices, if the Kafka Broker is alive, always when it regularly sends heartbeats requests. Also, while the Broker is the constraint to handle replication, it must be able to follow replication needs.\
1. Quotas\
In order to have different producing and consuming quotas, Kafka Broker allows some clients. This value is set in ZK under /config/clients path. Also, we can change it in bin/kafka-configs.sh script.\
1. Replicas\
However, for each topic, Zookeeper in Kafka keeps a set of in-sync replicas (ISR). Moreover, if somehow previously selected leader node fails then on the basis of currently live nodes Apache ZooKeeper will elect the new leader.\
1. Nodes and Topics Registry\
Basically, Zookeeper in Kafka stores nodes and topic registries. It is possible to find there all available brokers in Kafka and, more precisely, which Kafka topics are held by each broker, under /brokers/ids and /brokers/topics zNodes, they're stored. In addition, when it's started, Kafka broker create the register automatically.

### b. Kafka Consumers

1. Offsets\
ZooKeeper is the default storage engine, for consumer offsets, in Kafka's 0.9.1 release. However, all information about how many messages Kafka consumer consumes by each consumer is stored in ZooKeeper.\
2. Registry\
[Consumers in Kafka ](https://data-flair.training/blogs/kafka-consumer/)also have their own registry as in the case of Kafka Brokers. However, same rules apply to it, ie. as ephemeral zNode, it's destroyed once consumer goes down and the registration process is made automatically by the consumer.

4\. How does Kafka talk to ZooKeeper?
-------------------------------------

Here, we will see how Kafka classes are responsible for working with ZooKeeper. [Scala class ](https://data-flair.training/blogs/scala-case-class/)representing Kafka is KafkaServer. Its startup() method, initZk() contains a call to method initializing ZooKeeper connection. There are several methods in this algorithm which we use in this Zookeeper method. Hence, as a result, the method creates the temporary connection to ZooKeeper, in this case. This session is responsible for creating zNodes corresponding to chroot if it's miAfterwarderwards, this connection closes and creates the final connection held by the server.\
After, still inside initZk(), Kafka initializes all persistent zNodes, especially which server uses. We can retrieve there, among others: /consumers, /brokers/ids, /brokers/topics, /config, /admin/delete_topics, /brokers/seqid, /isr_change_notification, /config/topics, /config/clients.

Now, using synchronization to initialize other members, we can use this created ZooKeeper instance:

-   Replica manager
-   Config manager
-   Coordinator, and controller


# Kafka guarantees
* messages that sent into particular topic will be appended in the same order
* consumer see messages in order that were written
* "At-least-once" message delivery guaranteed - for consumer who crushed before it commited offset
* "At-most-once" delivery - ( custom realization ) consumer will never read the same message again, even when crushed before process it


# scripts
## start Kafka's Broker
```
zookeeper-server-start.sh
kafka-server-start.sh config/server.properties
```

## topic create
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic mytopic
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --describe --topic mytopic
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --config retention.ms=360000 --topic mytopic
```
or just enable "autocreation"
```
auto.create.topics.enable=true
```

## topic delete
can be marked "for deletion"
```
bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic mytopic
```

## topics list

```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --list
```

# topics describe
```
kafka-topics --describe --zookeeper localhost:2181 --topic mytopicname
```

## topic update
```
bin/kafka-topics.sh --alter --zookeeper localhost:2181 --partitions 5 --topic mytopic
bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic mytopic --config retention.ms=72000
bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic mytopic --deleteConfig retention.ms=72000
```

# [Producer](https://docs.confluent.io/current/clients/producer.html)
## producer console
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic mytopic
```
## java producer example
```
 Properties props = new Properties();
 props.put("bootstrap.servers", "localhost:4242");
 props.put("acks", "all");  // 0 - no wait; 1 - leader write into local log; all - leader write into local log and wait ACK from full set of InSyncReplications 
 props.put("client.id", "unique_client_id"); // nice to have
 props.put("retries", 0);           // can change ordering of the message in case of retriying
 props.put("batch.size", 16384);    // collect messages into batch
 props.put("linger.ms", 1);         // additional wait time before sending batch
 props.put("compression.type", ""); // type of compression: none, gzip, snappy, lz4
 props.put("buffer.memory", 33554432);
 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 Producer<String, String> producer = new KafkaProducer<>(props);
 producer.metrics(); // 
 for(int i = 0; i < 100; i++)
     producer.send(new ProducerRecord<String, String>("mytopic", Integer.toString(i), Integer.toString(i)));
     producer.flush(); // immediatelly send, even if 'linger.ms' is greater than 0
 producer.close();
 producer.partitionsFor("mytopic")
```
partition will be selected 

# Consumer
## [consumer console](https://github.com/apache/kafka/blob/trunk/core/src/main/scala/kafka/tools/ConsoleConsumer.scala) ( console consumer )
```
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic mytopic --from-beginning
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic mytopic --from-beginning --consumer.config my_own_config.properties
bin/kafka-console-consumer.sh --bootstrap-server mus07.mueq.adac.com:9092 --new-consumer --topic session-ingest-stage-1 --offset 20 --partition 0  --consumer.config kafka-log4j.properties

# read information about partitions
java kafka.tools.GetOffsetShell --broker-list musnn071001:9092 --topic session-ingest-stage-1
# get number of messages in partitions, partitions messages count
bin/kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic session-ingest-stage-1
```
## consumer group console
```
bin/kafka-consumer-groups.sh --zoopkeeper localhost:2181 --describe --group mytopic-consumer-group
```

## consumer offset
* automatic commit offset (enable.auto.commit=true) with period (auto.commit.interval.ms=1000)
* manual offset commit (enable.auto.commit=false) 

## [consumer configuration](https://kafka.apache.org/documentation/#consumerconfigs)
```
 Properties props = new Properties();
 props.put("bootstrap.servers", "localhost:4242"); // list of host/port pairs to connect to cluster
 props.put("client.id", "unique_client_id");       // nice to have
 props.put("group.id", "unique_group_id");         // nice to have
 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 props.put("fetch.min.bytes", 0);              // if value 1 - will be fetched immediatelly
 props.put("enable.auto.commit", "true");      //
 // timeout of detecting failures of consumer, Kafka group coordinator will wait for heartbeat from consumer within this period of time
 props.put("session.timeout.ms", "1000"); 
 // expected time between heartbeats to the consumer coordinator,
 // is consumer session stays active, 
 // facilitate rebalancing when new consumers join/leave group,
 // must be set lower than *session.timeout.ms*
 props.put("heartbeat.interval.ms", "");
```

## consumer java
NOT THREAD Safe !!!
```
KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
ConsumerRecords<String, String> records = consumer.pool(100); // time in ms

```

## consumer consume messages
* by topic
```
consumer.subscribe(Arrays.asList("mytopic_1", "mytopic_2"));
```
* by partition
```
TopicPartition partition0 = new TopicPartition("mytopic_1", 0);
TopicPartition partition1 = new TopicPartition("mytopic_1", 1);
consumer.assign(Arrays.asList(partition0, partition1));
```
* seek to position
```
seek(partition0, 1024);
seekToBeginning(parition0, partition1);
seekToEnd(parition0, partition1);
```

# Kafka connect
* manage copying data between Kafka and another system
* connector either a source or sink
* connector can split "job" to "tasks" ( to copy subset of data )
* *partitioned streams* for source/sink, each record into it: [key,value,offset]
* standalone/distributed mode

## Kafka connect standalone
start connect
```
bin/connect-standalone.sh config/connect-standalone.properties config/connect-file-source.properties
```
connect settings
```
name=local-file-source
connector.class=org.apache.kafka.connect.file.FileStreamSourceConnector
tasks.max=1
file=my_test_file.txt
topic=topic_for_me
```
after execution you can check the topic
```
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic topic_for_me --from-beginning
```