package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureRegistrationApplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRegistrationManager lectureRegistrationManager;

    public LectureRegistrationApplyResponseDto applyForLectureRegistration(long lectureId, long userId) {
        return lectureRegistrationManager.register(lectureId, userId);
    }
}
