package kafkaworkshop;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

public class AvroWithoutRegistryUserProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ACKS_CONFIG, "all");
        props.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, MyCustomUserSerializer.class.getName());

        User user = User.newBuilder()
                .setName("andre")
                .setAge(101)
                .setFavoriteColor(Color.GREEN)
                .build();

        Producer<String, User> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<>("users-avro-without-registry", user.getName().toString(), user));
        producer.close();
    }
}

class MyCustomUserSerializer implements Serializer<User> {
    @Override
    public byte[] serialize(String topic, User user) {
        if (user == null) {
            return null;
        }

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);

            DatumWriter<User> datumWriter = new SpecificDatumWriter<>(User.class);
            datumWriter.write(user, binaryEncoder);

            binaryEncoder.flush();
            byteArrayOutputStream.close();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException ex) {
            throw new SerializationException("Unable to serialize data='" + user + "' for topic='" + topic + "'", ex);
        }
    }
}
