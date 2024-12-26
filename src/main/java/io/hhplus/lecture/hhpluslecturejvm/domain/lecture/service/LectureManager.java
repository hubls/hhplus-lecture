package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.Lecture;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LectureManager {
    private final LectureRepository lectureRepository;

    public List<LectureDto> getAvailableLectures(Timestamp date) {
        List<Lecture> lectures = lectureRepository.findAvailableLectures(date);
        return lectures.stream()
                .map(lecture -> new LectureDto(
                        lecture.getId(),
                        lecture.getTitle(),
                        lecture.getInstructorId(),
                        lecture.getStartDate(),
                        lecture.getEndDate(),
                        lecture.getCapacity()))
                .collect(Collectors.toList());
    }
}
