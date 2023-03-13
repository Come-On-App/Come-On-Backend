package com.comeon.backend.meeting.infrastructure.domain.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.MemberListUpdateEvent;
import com.comeon.backend.meeting.infrastructure.domain.event.message.MemberListUpdatedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class MemberListUpdateEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MemberListUpdateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MemberListUpdateEvent event) {
        String topic = topics.getMeetingMembers();
        MemberListUpdatedMessage message = new MemberListUpdatedMessage(event.getMeetingId());

        producer.produce(topic, message);
    }
}
