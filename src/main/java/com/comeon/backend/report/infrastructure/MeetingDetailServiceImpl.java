package com.comeon.backend.report.infrastructure;

import com.comeon.backend.meeting.query.dao.MeetingDao;
import com.comeon.backend.meeting.query.dto.MeetingDetailForReport;
import com.comeon.backend.report.command.domain.MeetingDetail;
import com.comeon.backend.report.command.domain.MeetingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingDetailServiceImpl implements MeetingDetailService {

    private final MeetingDao meetingDao;

    @Override
    public MeetingDetail findMeetingDetailBy(Long meetingId) {
        MeetingDetailForReport meetingDetailForReport = meetingDao.findMeetingDetailForReport(meetingId);

        List<MeetingDetail.MemberDetails> members = getAndConvertMembers(meetingDetailForReport);
        List<MeetingDetail.PlaceDetails> places = getAndConvertPlaces(meetingDetailForReport);
        MeetingDetail.MeetingMetaData meetingMetaData = getAndConvertMeetingMetaData(meetingDetailForReport);

        return new MeetingDetail(meetingMetaData, members, places);
    }

    private MeetingDetail.MeetingMetaData getAndConvertMeetingMetaData(MeetingDetailForReport meetingDetailForReport) {
        return new MeetingDetail.MeetingMetaData(
                meetingDetailForReport.getMeetingMetaData().getMeetingId(),
                meetingDetailForReport.getMeetingMetaData().getThumbnailImageUrl(),
                meetingDetailForReport.getMeetingMetaData().getMeetingName(),
                meetingDetailForReport.getMeetingMetaData().getMeetingStartTime(),
                new MeetingDetail.HostUserSimple(
                        meetingDetailForReport.getMeetingMetaData().getHostUser().getUserId(),
                        meetingDetailForReport.getMeetingMetaData().getHostUser().getNickname(),
                        meetingDetailForReport.getMeetingMetaData().getHostUser().getProfileImageUrl()
                ),
                new MeetingDetail.MeetingCalendar(
                        meetingDetailForReport.getMeetingMetaData().getCalendar().getStartFrom(),
                        meetingDetailForReport.getMeetingMetaData().getCalendar().getEndTo()
                ),
                meetingDetailForReport.getMeetingMetaData().getFixedDate() != null ?
                        new MeetingDetail.ConfirmedDate(
                                meetingDetailForReport.getMeetingMetaData().getFixedDate().getStartFrom(),
                                meetingDetailForReport.getMeetingMetaData().getFixedDate().getEndTo()
                        ) :
                        null
        );
    }

    private List<MeetingDetail.PlaceDetails> getAndConvertPlaces(MeetingDetailForReport meetingDetailForReport) {
        List<MeetingDetail.PlaceDetails> places = meetingDetailForReport.getPlaces().stream()
                .map(p -> new MeetingDetail.PlaceDetails(
                        p.getMeetingPlaceId(),
                        p.getPlaceName(),
                        p.getMemo(),
                        p.getLat(),
                        p.getLng(),
                        p.getAddress(),
                        p.getOrder(),
                        p.getCategory(),
                        p.getGooglePlaceId()
                ))
                .collect(Collectors.toList());
        return places;
    }

    private List<MeetingDetail.MemberDetails> getAndConvertMembers(MeetingDetailForReport meetingDetailForReport) {
        List<MeetingDetail.MemberDetails> members = meetingDetailForReport.getMembers().stream()
                .map(m -> new MeetingDetail.MemberDetails(
                        m.getMemberId(),
                        m.getUserId(),
                        m.getNickname(),
                        m.getProfileImageUrl(),
                        m.getMemberRole()
                ))
                .collect(Collectors.toList());
        return members;
    }
}
