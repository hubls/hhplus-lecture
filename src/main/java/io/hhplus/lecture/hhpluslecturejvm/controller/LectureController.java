package io.hhplus.lecture.hhpluslecturejvm.controller;

import io.hhplus.lecture.hhpluslecturejvm.controller.dto.LectureRegistrationApplyRequest;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureCompletedDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureRegistrationApplyResponseDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

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

    /**
     * 특강 신청 가능 목록 API
     */
    @PostMapping("/")
    public ResponseEntity<List<LectureDto>> getAvailableLectures(@RequestBody Timestamp date) {
        return ok(lectureService.getAvailableLectures(date));
    }

    /**
     * 특강 신청 완료 목록 조회 API
     */

    @GetMapping("/{userId}/completed")
    public ResponseEntity<List<LectureCompletedDto>> getCompletedLectures(
            @PathVariable long userId
    ) {
        return ok(lectureService.getCompletedLectures(userId));
    }
}
