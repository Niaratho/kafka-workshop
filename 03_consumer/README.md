# 3 - Consuming Events

## Level 3.1 - Introduction to Consumers
For starters, let's implement a consumer that simply prints the content of a
topic to the console - similar to the functionality of the 
`kafka-console-consumer`.

1. Add some basic logic to the `SimpleConsumerApp` and let it consume the content of the `numbers` topic
   (see exercise 2.1).
2. Set property `auto.offset.reset` to `earliest` to allow consuming messages that have been produced before the
   consumer was started.
3. Start the application and check its output.


## Level 3.2 - Joining Forces
Usually, producing messages is much faster than consuming and processing them. By writing messages to different
partitions, we can use multiple consumers to process messages in parallel and scale the consumer side. For this, we
must enable the consumer to join a group.

**Tasks**
1. Update the `SimpleConsumerApp` to allow multiple instances to work together in groups.
2. To make the effects of multiple consumers working together more visible, add a sleeping period (e.g. 250 ms)
   before consuming the next messages. This simulates the computation work a regular consumer would have to do.
3. Update the `SimpleProducerApp` to continuously send random numbers. Start several fast and slow consumers in
   different groups and consume the content of the `numbers` topic.
4. Use the `kafka-consumer-groups` command to monitor the consumer offsets and lags of the groups.
5. Dynamically add and remove group members by starting and stopping consumer instances. Observe the rebalancing
   behaviour.
