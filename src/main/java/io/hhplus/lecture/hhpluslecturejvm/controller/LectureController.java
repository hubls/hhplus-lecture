package io.hhplus.lecture.hhpluslecturejvm.controller;

import io.hhplus.lecture.hhpluslecturejvm.controller.dto.LectureRegistrationApplyRequest;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureRegistrationApplyResponseDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    /**
     * 특강 신청 API
     */
    @PostMapping("/apply")
    public ResponseEntity<LectureRegistrationApplyResponseDto> apply(@RequestBody @Valid LectureRegistrationApplyRequest request) {
        return ok(lectureService.applyForLectureRegistration(request.lectureId(), request.userId()));
    }
}
