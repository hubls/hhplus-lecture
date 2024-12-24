package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.repository;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureCompletedDto;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.LectureRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureRegistrationRepository extends JpaRepository<LectureRegistration, Long> {

    // 현재 특정 강의의 신청 인원을 조회
    @Query("SELECT COUNT(lr) FROM LectureRegistration lr WHERE lr.lectureId = :lectureId")
    long countByLectureId(@Param("lectureId") long lectureId);

    // 특정 사용자와 강의의 중복 신청 여부 확인
    boolean existsByLectureIdAndUserId(long lectureId, long userId);

    List<LectureRegistration> findByUserId(long userId);
}