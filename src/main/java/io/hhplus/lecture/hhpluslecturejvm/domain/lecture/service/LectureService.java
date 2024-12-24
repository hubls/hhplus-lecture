package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureRegistrationApplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRegistrationManager lectureRegistrationManager;
    private final LectureManager lectureManager;

    public LectureRegistrationApplyResponseDto applyForLectureRegistration(long lectureId, long userId) {
        return lectureRegistrationManager.register(lectureId, userId);
    }

    public List<LectureDto> getAvailableLectures(Timestamp date) {
        return lectureManager.getAvailableLectures(date);
    }
}
