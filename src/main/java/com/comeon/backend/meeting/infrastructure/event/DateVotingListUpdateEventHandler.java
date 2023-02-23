package com.comeon.backend.meeting.infrastructure.event;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.meeting.command.domain.event.DateVotingListUpdateEvent;
import com.comeon.backend.meeting.infrastructure.event.message.DateVotingListUpdatedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class DateVotingListUpdateEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = DateVotingListUpdateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(DateVotingListUpdateEvent event) {
        String topic = topics.getMeetingVoting();
        DateVotingListUpdatedMessage message =
                new DateVotingListUpdatedMessage(event.getMeetingId(), event.getDate());

        producer.produce(topic, message);
    }
}
