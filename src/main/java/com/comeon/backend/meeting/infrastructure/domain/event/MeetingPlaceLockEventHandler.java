package com.comeon.backend.meeting.infrastructure.domain.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.MeetingPlaceLockEvent;
import com.comeon.backend.meeting.infrastructure.domain.event.message.MeetingPlaceLockMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingPlaceLockEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingPlaceLockEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingPlaceLockEvent event) {
        String topic = topics.getMeetingPlaceLock();
        MeetingPlaceLockMessage message =
                new MeetingPlaceLockMessage(event.getMeetingId(), event.getMeetingPlaceId(), event.getUserId());

        producer.produce(topic, message);
    }
}
