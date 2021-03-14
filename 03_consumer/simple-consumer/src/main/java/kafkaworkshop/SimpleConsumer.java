package kafkaworkshop;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

public class SimpleConsumer {
    private static final Logger log = LoggerFactory.getLogger(SimpleConsumer.class);

    public static void main(String[] args) {
        solution_3_1();
//        solution_3_2();
    }

    private static void solution_3_1() {
        Properties props = new Properties();
        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        props.setProperty(GROUP_ID_CONFIG, "my-first-consumer");
        props.setProperty(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("numbers"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                log.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
            }
        }
    }


    private static void solution_3_2() {
        new NoopConsumer("fast-1", "very-fast", 5).start();
        new NoopConsumer("fast-2", "very-fast", 5).start();
        new NoopConsumer("fast-3", "very-fast", 5).start();

        new NoopConsumer("slow-1", "slow", 50).start();
        new NoopConsumer("slow-2", "slow", 50).start();
        new NoopConsumer("slow-3", "slow", 50).start();
        new NoopConsumer("slow-4", "slow", 50).start();
        new NoopConsumer("slow-5", "slow", 50).start();
        new NoopConsumer("slow-6", "slow", 50).start();
    }
}

class NoopConsumer extends Thread {
    private static final Logger log = LoggerFactory.getLogger(NoopConsumer.class);

    private final KafkaConsumer<String, String> consumer;
    private final String consumerName;
    private final int delayDurationInMillis;


    public NoopConsumer(String consumerName, String consumerGroupId, int delayDurationInMillis) {
        Properties props = new Properties();
        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        props.setProperty(GROUP_ID_CONFIG, consumerGroupId);
        props.setProperty(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("numbers"));

        this.consumerName = consumerName;
        this.delayDurationInMillis = delayDurationInMillis;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                if (++count % 1000 == 0) {
                    log.info("{} has processed {} entries", consumerName, count);
                }

                try {
                    Thread.sleep(delayDurationInMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
