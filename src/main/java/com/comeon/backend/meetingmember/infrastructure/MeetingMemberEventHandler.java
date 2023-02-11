package com.comeon.backend.meetingmember.infrastructure;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meetingmember.command.domain.MeetingMemberEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class MeetingMemberEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingMemberEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingMemberEvent event) {
        String topic = topics.getMeetingMembers();
        MemberListUpdateMessage message = new MemberListUpdateMessage(event.getMeetingId());
        producer.produce(topic, message);
    }
}
