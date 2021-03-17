package kafkaworkshop;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class AvroWithoutRegistryUserDeserializer implements Deserializer<User> {

    @Override
    public User deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(data, null);
            DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);

            return userDatumReader.read(null, binaryDecoder);
        } catch (IOException e) {
            throw new SerializationException("Unable to deserialize data from topic='" + topic + "'", e);
        }
    }
}
