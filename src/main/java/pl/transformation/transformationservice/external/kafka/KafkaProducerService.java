package pl.transformation.transformationservice.external.kafka;

public interface KafkaProducerService {
    void send(String topic, String message);
}
