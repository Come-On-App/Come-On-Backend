package com.comeon.backend.place.infrastructure;

import com.comeon.backend.common.kafka.MeetingResourceUpdatedMessage;
import com.comeon.backend.common.kafka.TargetResourceOfMeeting;
import com.comeon.backend.common.kafka.KafkaProducer;
import com.comeon.backend.common.kafka.KafkaTopicProperties;
import com.comeon.backend.place.command.domain.PlacesUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class PlacesUpdateEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = PlacesUpdateEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(PlacesUpdateEvent event) {
        producer.produce(
                topics.getMeetingPlaces(),
                new MeetingResourceUpdatedMessage(
                        event.getTargetMeetingId(),
                        TargetResourceOfMeeting.PLACES
                )
        );
    }
}
