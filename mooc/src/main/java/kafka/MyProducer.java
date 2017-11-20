package kafka;




import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MyProducer {
    public static void main(String[] args) {


        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.204.3:9092");
        props.put("transactional.id", "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<String, String>(props, new StringSerializer(), new StringSerializer());


        producer.initTransactions();

        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++)
                producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
            producer.commitTransaction();
        } catch ( Exception  e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        }
        producer.close();
    }
}