package pl.transformation.transformationservice.external.kafka;

import org.springframework.kafka.core.KafkaTemplate;

public class TransformKafkaProducerService implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TransformKafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
