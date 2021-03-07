# 2 - Writing Messages to Kafka

## Level 2.1 - Introduction to Producers
Implement a small application that send some messages to a Kafka topic.

1. Create a topic `numbers` with 3 replicas and at least 3 partitions.
2. Update the `SimpleProducerApp` and let it write 100 numbers to the topic.
3. Use the "kafka-console-consumer" to consume these numbers. **How to explain the order of the output?**

**Bonus**
- The app can be run as a standalone executable and can be configured via env variables. The following parameters can
  be passed:
    - bootstrap servers
    - name of the topic to send to
    - number of messages to send
- Create a Kubernetes `Job` using that application.


## Level 2.2 - Throughput vs. durability
In Kafka, many trade-offs can be made in terms of performance and message durability. Some of these aspects pertain
the producers of messages.

Let's examine the effects of various producer config properties and the impact of synchronous and asynchronous sends.

**Key Results**
1. Add a stopwatch (e.g. org.apache.commons:commons-lang3) to the producer app which measures the duration of the
   send process.
2. Measure the time it takes to send one million messages **asynchronously** using different producer configs:
    - `acks`: 0, 1, all
    - `batch.size`: 20, 200, 2000, 20000
3. Repeat the experiment using a **sync** producer instead of an async one. **How to explain the difference?**
