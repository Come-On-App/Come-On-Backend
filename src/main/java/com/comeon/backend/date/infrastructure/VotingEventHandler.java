package com.comeon.backend.date.infrastructure;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.date.command.domain.voting.DateVotingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class VotingEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = DateVotingEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(DateVotingEvent event) {
        String topic = topics.getMeetingVoting();
        VotingListUpdateMessage message =
                new VotingListUpdateMessage(event.getTargetMeetingId(), event.getTargetDate());
        producer.produce(topic, message);
    }
}
