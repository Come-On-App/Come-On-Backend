package com.comeon.backend.meeting.infrastructure.domain.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.MeetingPlaceListUpdateEvent;
import com.comeon.backend.meeting.infrastructure.domain.event.message.MeetingPlaceListUpdatedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class MeetingPlaceListUpdateEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingPlaceListUpdateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingPlaceListUpdateEvent event) {
        String topic = topics.getMeetingPlaces();
        MeetingPlaceListUpdatedMessage message =
                new MeetingPlaceListUpdatedMessage(event.getMeetingId());

        producer.produce(topic, message);
    }
}
