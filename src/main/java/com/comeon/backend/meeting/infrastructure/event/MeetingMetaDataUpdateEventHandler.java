package com.comeon.backend.meeting.infrastructure.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.MeetingMetaDataUpdateEvent;
import com.comeon.backend.meeting.infrastructure.event.message.MeetingMetaDataUpdatedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingMetaDataUpdateEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingMetaDataUpdateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingMetaDataUpdateEvent event) {
        String topic = topics.getMeetingMetaData();
        MeetingMetaDataUpdatedMessage message =
                new MeetingMetaDataUpdatedMessage(event.getMeetingId());

        producer.produce(topic, message);
    }
}
