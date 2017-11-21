package kafka3;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Kafka生产者
 */
public class KafkaProducer extends Thread {

    private String topic;

    private org.apache.kafka.clients.producer.KafkaProducer<Integer, String> producer;

    public KafkaProducer(String topic) {
        this.topic = topic;

        Properties properties = new Properties();

        properties.put("metadata.broker.list", KafkaProperties.BROKER_LIST);
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        properties.put("bootstrap.servers", "192.168.204.3:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // producer = new KafkaProducer<Integer, String>(new ProducerConfig(properties));
        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);

    }


    @Override
    public void run() {

        int messageNo = 1;

        while (true) {
            String message = "message_" + messageNo;
            producer.send(new ProducerRecord<Integer, String>(topic, message));
            System.out.println("Sent: " + message);

            messageNo++;

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
