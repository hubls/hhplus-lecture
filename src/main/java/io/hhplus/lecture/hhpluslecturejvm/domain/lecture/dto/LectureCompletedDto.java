package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto;

public record LectureCompletedDto(
        Long id, // 특강 ID
        String name, // 특강 이름
        String instructorId // 강연자 정보
) {
}
