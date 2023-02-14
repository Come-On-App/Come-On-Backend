package com.comeon.backend.meeting.infrastructure;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.MeetingInfoModifyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingInfoModifyEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingInfoModifyEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingInfoModifyEvent event) {
        String topic = topics.getMeetingMetaData();
        MeetingInfoUpdateMessage message =
                new MeetingInfoUpdateMessage(event.getMeetingId());
        producer.produce(topic, message);
    }
}
