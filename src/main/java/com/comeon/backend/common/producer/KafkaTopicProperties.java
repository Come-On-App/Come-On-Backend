package com.comeon.backend.common.producer;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KafkaTopicProperties {

    @Value("${kafka.topic.meeting-meta-data}")
    private String meetingMetaData;

    @Value("${kafka.topic.meeting-members}")
    private String meetingMembers;

    @Value("${kafka.topic.meeting-places}")
    private String meetingPlaces;

    @Value("${kafka.topic.meeting-voting}")
    private String meetingVoting;

    @Value("${kafka.topic.meeting-fixed-date}")
    private String meetingFixedDate;

    @Value("${kafka.topic.meeting-place-lock}")
    private String meetingPlaceLock;

    @Value("${kafka.topic.meeting-place-unlock}")
    private String meetingPlaceUnlock;

    @Value("${kafka.topic.meeting-time}")
    private String meetingTime;

    @Value("${kafka.topic.member-drop}")
    private String memberDrop;
}
