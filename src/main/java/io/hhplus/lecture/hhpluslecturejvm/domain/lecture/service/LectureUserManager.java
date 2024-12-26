package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureCompletedDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.Lecture;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.LectureRegistration;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.repository.LectureRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LectureUserManager {
    private final LectureRegistrationRepository lectureRegistrationRepository;

    public List<LectureCompletedDto> getCompletedLectures(long userId) {
        List<LectureRegistration> lectureRegistrations = lectureRegistrationRepository.findByUserId(userId);
        return lectureRegistrations.stream()
                .map(lectureRegistration -> new LectureCompletedDto(
                        lectureRegistration.getLectureId(),
                        lectureRegistration.getLectureTitle(),
                        lectureRegistration.getInstructorId()
                    ))
                .collect(Collectors.toList());
    }
}
