package com.comeon.backend.date.infrastructure;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.date.command.domain.confirm.FixedDateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class FixedDateEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = FixedDateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(FixedDateEvent event) {
        String topic = topics.getMeetingFixedDate();
        MeetingFixedDateUpdateMessage message =
                new MeetingFixedDateUpdateMessage(event.getMeetingId());
        producer.produce(topic, message);
    }
}
