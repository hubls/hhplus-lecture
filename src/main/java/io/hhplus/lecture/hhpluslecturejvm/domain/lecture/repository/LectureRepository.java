package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.repository;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query("SELECT l FROM Lecture l WHERE l.id = :lectureId")
    Optional<Lecture> findByIdWithLock(@Param("lectureId") long lectureId);
}
