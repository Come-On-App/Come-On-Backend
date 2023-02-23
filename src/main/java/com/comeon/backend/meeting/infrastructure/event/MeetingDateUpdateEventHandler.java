package com.comeon.backend.meeting.infrastructure.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.MeetingDateUpdateEvent;
import com.comeon.backend.meeting.infrastructure.event.message.MeetingDateUpdatedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class MeetingDateUpdateEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingDateUpdateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingDateUpdateEvent event) {
        String topic = topics.getMeetingFixedDate();
        MeetingDateUpdatedMessage message = new MeetingDateUpdatedMessage(event.getMeetingId());

        producer.produce(topic, message);
    }
}
