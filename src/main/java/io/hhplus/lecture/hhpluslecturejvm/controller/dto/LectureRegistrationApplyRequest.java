package io.hhplus.lecture.hhpluslecturejvm.controller.dto;

import jakarta.validation.constraints.NotNull;

public record LectureRegistrationApplyRequest(
        @NotNull
        long lectureId,
        @NotNull
        long userId
) {
}
