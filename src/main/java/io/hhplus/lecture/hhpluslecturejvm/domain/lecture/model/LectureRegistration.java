package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(
        name = "lectures_registrations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lecture_id"}) // 중복 신청 방지
)
public class LectureRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 신청 ID

    @Column(name = "user_id", nullable = false)
    private Long userId; // 신청한 사용자 ID

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId; // 신청한 특강 ID

    @Column(name = "instructor_id", nullable = false)
    private Long instructorId; // 강사 ID

    @Column(name = "lecture_title", nullable = false)
    private String lectureTitle; // 특강 이름

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt; // 신청 시간

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt; // 마지막 업데이트 시간

    public LectureRegistration() {

    }

    public LectureRegistration(Long userId, Long lectureId, Long instructorId, String lectureTitle) {
        this.userId = userId;
        this.lectureId = lectureId;
        this.instructorId = instructorId;
        this.lectureTitle = lectureTitle;
    }
}