package io.hhplus.lecture.hhpluslecturejvm.domain.lecture;

import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.model.LectureRegistration;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.repository.LectureRegistrationRepository;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service.LectureService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
public class ConcurrentTest {
    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureRegistrationRepository lectureRegistrationRepository;

    @AfterEach
    void cleanUp() {
        // 테스트 후 데이터 정리
        lectureRegistrationRepository.deleteAll();
    }

    @Test
    void 동시에_40명이_특강을_신청할_때_정확히_30명만_성공해야_한다() throws InterruptedException {
        // given
        Long lectureId = 1L; // 테스트할 특강 ID

        // when
        int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        /*
        40개의 스레드가 개별적으로 등록 요청을 처리하고, 각 스레드가 완료되면 latch.countDown()이 호출되게 한다.
        마지막 스레드가 완료되면 latch.await() 가 해제된다.
        스레드의 순서를 고려하지 않고, 각 스레드가 개별적으로 작업을 수행한 후 결과를 확인하고자 채택함.
         */
        for (int i = 1; i <= threadCount; i++) {
            int userId = i;
            executorService.submit(() -> {
                try {
                    lectureService.applyForLectureRegistration(lectureId, userId);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(); // 모든 스레드가 끝날 때까지 대기

//        // then
//        // 특강 정원이 정확히 30명인지 확인
        long lectureRegistrationCount = lectureRegistrationRepository.countByLectureId(lectureId);
        assertThat(lectureRegistrationCount).isEqualTo(30);
    }

    @Test
    void 동일한_학생이_같은_특강을_동시에_5번_신청할_때_1번만_성공해야한다() throws InterruptedException {
        Long lectureId = 1L; // 테스트할 특강 ID
        long userId = 123L;

        // when
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            executorService.submit(() -> {
                try {
                    lectureService.applyForLectureRegistration(lectureId, userId);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(); // 모든 스레드가 끝날 때까지 대기

        // 등록된 수 확인
        List<LectureRegistration> registrationCount = lectureRegistrationRepository.findByUserId(userId);
        assertThat(registrationCount).hasSize(1); // 성공한 등록 수는 1이어야 함

    }

}
