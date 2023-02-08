package com.comeon.backend.common.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, MeetingResourceUpdatedMessage> kafkaTemplate;

    public void produce(String topic, MeetingResourceUpdatedMessage payload) {
        kafkaTemplate.send(topic, payload);
    }
}
