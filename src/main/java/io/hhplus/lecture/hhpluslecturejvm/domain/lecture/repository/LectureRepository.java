package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.repository;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query("SELECT l FROM Lecture l WHERE l.id = :lectureId")
    Optional<Lecture> findByIdWithLock(@Param("lectureId") long lectureId);

    @Query("SELECT l " +
            "FROM Lecture l " +
            "LEFT JOIN LectureRegistration lr ON l.id = lr.lectureId " +
            "WHERE :date BETWEEN l.startDate AND l.endDate " +
            "GROUP BY l.id " +
            "HAVING COUNT(lr.id) < l.capacity")
    List<Lecture> findAvailableLectures(@Param("date") Timestamp date);
}
