package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "lectures")
@Data
public class Lecture {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // 특강 ID

        @Column(nullable = false, length = 200)
        private String title; // 특강 제목

        @Column(nullable = false)
        private Long instructorId; // 강연자 ID

        @Lob
        private String description; // 특강 설명

        @Column(nullable = false)
        private Timestamp startDate; // 특강 시작 시간

        @Column(nullable = false)
        private Timestamp endDate; // 특강 종료 시간

        @Column(nullable = false, columnDefinition = "INT DEFAULT 30")
        private int capacity; // 수강 정원

        @Column(nullable = false, updatable = false)
        private Timestamp createdAt; // 특강 생성 시간

        @Column(nullable = false)
        private Timestamp updatedAt; // 마지막 업데이트 시간
}
