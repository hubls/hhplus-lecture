package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureRegistrationApplyResponseDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.Lecture;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.LectureRegistration;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.repository.LectureRegistrationRepository;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.repository.LectureRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LectureRegistrationManager {
    private final LectureRepository lectureRepository;
    private final LectureRegistrationRepository lectureRegistrationRepository;

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public LectureRegistrationApplyResponseDto register(long lectureId, long userId) {
        // 강의 조회 및 유효성 검사
        Optional<LectureRegistrationApplyResponseDto> lectureValidationResponse = validateLecture(lectureId);
        if (lectureValidationResponse.isPresent()) {
            return lectureValidationResponse.get(); // 강의가 없을 경우 DTO 반환
        }

        // 강의가 유효할 경우
        Lecture lecture = lectureRepository.findByIdWithLock(lectureId).get();

        // 등록 유효성 검사
        Optional<LectureRegistrationApplyResponseDto> validationResponse = validateRegistration(lecture, lectureId, userId);
        if (validationResponse.isPresent()) {
            return validationResponse.get(); // 유효성 검사에서 실패한 경우 반환
        }

        // 새로운 신청 추가
        LectureRegistration registration = new LectureRegistration(lectureId, userId);
        lectureRegistrationRepository.save(registration);

        // 성공적으로 등록
        return new LectureRegistrationApplyResponseDto(true, "특강 신청이 완료되었습니다.");
    }

    // 강의 유효성 검사
    private Optional<LectureRegistrationApplyResponseDto> validateLecture(long lectureId) {
        // 강의 조회
        Optional<Lecture> lecture = lectureRepository.findByIdWithLock(lectureId);

        // 강의가 존재하면 빈 Optional 반환, 존재하지 않으면 오류 메시지 반환
        if (lecture.isEmpty()) {
            return Optional.of(new LectureRegistrationApplyResponseDto(false, "존재하지 않는 강의입니다."));
        }

        // 강의가 존재하면 유효성 통과
        return Optional.empty();
    }

    // 등록 유효성 검사
    private Optional<LectureRegistrationApplyResponseDto> validateRegistration(Lecture lecture, long lectureId, long userId) {
        // 1. 현재 등록 인원 확인 (비관적 락으로 트랜잭션 내에서 안전)
        long currentRegistrationCount = lectureRegistrationRepository.countByLectureId(lectureId);
        if (currentRegistrationCount > lecture.getCapacity()) {
            return Optional.of(new LectureRegistrationApplyResponseDto(false, "신청 정원이 초과되었습니다."));
        }

        // 2. 이미 신청한 사용자인지 확인
        boolean alreadyRegistered = lectureRegistrationRepository.existsByLectureIdAndUserId(lectureId, userId);
        if (alreadyRegistered) {
            return Optional.of(new LectureRegistrationApplyResponseDto(false, "이미 신청한 사용자입니다."));
        }

        // 유효성 검사 통과 시 빈 Optional 반환
        return Optional.empty();
    }

}
