package pl.transformation.transformationservice.external.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

class TransformKafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    private TransformKafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kafkaProducerService = new TransformKafkaProducerService(kafkaTemplate);
    }

    @Test
    void send_ShouldSendMessageToKafkaTopic() {
        // given
        String topic = "test-topic";
        String message = "Test message";
        // when
        kafkaProducerService.send(topic, message);
        // then
        verify(kafkaTemplate).send(topic, message);
    }
}