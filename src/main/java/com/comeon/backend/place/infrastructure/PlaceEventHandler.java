package com.comeon.backend.place.infrastructure;

import com.comeon.backend.common.producer.KafkaProducer;
import com.comeon.backend.common.producer.KafkaTopicProperties;
import com.comeon.backend.place.command.domain.MeetingPlaceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class PlaceEventHandler {

    private final KafkaProducer producer;
    private final KafkaTopicProperties topics;

    @Async
    @TransactionalEventListener(
            classes = MeetingPlaceEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(MeetingPlaceEvent event) {
        String topic = topics.getMeetingPlaces();
        PlaceListUpdateMessage message =
                new PlaceListUpdateMessage(event.getTargetMeetingId());
        producer.produce(topic, message);
    }
}
