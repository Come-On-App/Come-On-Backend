package com.comeon.backend.meeting.infrastructure.domain.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.MeetingTimeUpdateEvent;
import com.comeon.backend.meeting.infrastructure.domain.event.message.MeetingTimeUpdatedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingTimeUpdateEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingTimeUpdateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingTimeUpdateEvent event) {
        String topic = topics.getMeetingTime();
        MeetingTimeUpdatedMessage message =
                new MeetingTimeUpdatedMessage(event.getMeetingId());

        producer.produce(topic, message);
    }
}
