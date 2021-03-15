# kafka-workshop

## Command Cheat Sheet
#### Topics
```
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 3 --topic my-topic
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic my_topic
bin/kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic my-topic
```

#### Consumer Groups
```
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-group
```

#### Console Consumer
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my-topic
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my-topic --group my-group
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --property print.key=true --property key.separator=: --topic my-topic
```

#### Console Producer
```
bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic my-topic
bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --property parse.key=true --property key.separator=: --topic my-topic
```
