package com.comeon.backend.meeting.infrastructure.domain.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.MemberDropEvent;
import com.comeon.backend.meeting.infrastructure.domain.event.message.MemberDropMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class MemberDropEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MemberDropEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MemberDropEvent event) {
        String topic = topics.getMemberDrop();
        MemberDropMessage message = new MemberDropMessage(event.getMeetingId(), event.getUserId());

        producer.produce(topic, message);
    }
}
