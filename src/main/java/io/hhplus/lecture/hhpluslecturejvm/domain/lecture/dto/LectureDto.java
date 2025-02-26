package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto;

import java.sql.Timestamp;

public record LectureDto(
        Long id,
        String title,
        Long instructorId,
        Timestamp startDate,
        Timestamp endDate,
        int capacity
) {
}
