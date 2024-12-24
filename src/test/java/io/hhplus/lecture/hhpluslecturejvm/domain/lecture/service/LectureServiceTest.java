package io.hhplus.lecture.hhpluslecturejvm.domain.lecture.service;
import io.hhplus.lecture.hhpluslecturejvm.domain.lecture.dto.LectureRegistrationApplyResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {
    @Mock
    private LectureRegistrationManager lectureRegistrationManager;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("성공 응답")
    public void testApplyForLectureRegistration_Success() {
        // given: 강의 ID와 사용자 ID가 주어짐
        long lectureId = 1L;
        long userId = 123L;

        LectureRegistrationApplyResponseDto responseDto = new LectureRegistrationApplyResponseDto(true, "특강 신청이 완료되었습니다.");

        // when: lectureRegistrationManager.apply 메서드가 호출되면 성공적인 응답을 반환하도록 설정
        when(lectureRegistrationManager.register(lectureId, userId)).thenReturn(responseDto);

        // then: 실제 서비스 메서드를 호출하고, 반환값이 예상과 일치하는지 확인
        LectureRegistrationApplyResponseDto result = lectureService.applyForLectureRegistration(lectureId, userId);

        assertTrue(result.success());
        assertEquals("특강 신청이 완료되었습니다.", result.message());

        // verify: 의존성 메서드가 호출되었는지 확인
        verify(lectureRegistrationManager, times(1)).register(lectureId, userId);
    }

    @Test
    @DisplayName("존재하지 않는 강의")
    public void testApplyForLectureRegistration_Failure_LectureNotFound() {
        // given: 존재하지 않는 강의 ID와 사용자 ID
        long lectureId = 999L;  // 존재하지 않는 강의 ID
        long userId = 123L;

        // 강의가 존재하지 않으면 "존재하지 않는 강의입니다."라는 메시지를 반환하는 응답
        LectureRegistrationApplyResponseDto responseDto = new LectureRegistrationApplyResponseDto(false, "존재하지 않는 강의입니다.");

        // when: lectureRegistrationManager.register 메서드가 호출되면 강의가 없는 경우에 대한 응답을 반환하도록 설정
        when(lectureRegistrationManager.register(lectureId, userId)).thenReturn(responseDto);

        // then: "존재하지 않는 강의입니다."라는 메시지가 제대로 반환되는지 확인
        LectureRegistrationApplyResponseDto result = lectureService.applyForLectureRegistration(lectureId, userId);

        assertFalse(result.success());
        assertEquals("존재하지 않는 강의입니다.", result.message());

        // verify: lectureRegistrationManager.register 메서드가 정확히 한 번 호출되었는지 확인
        verify(lectureRegistrationManager, times(1)).register(lectureId, userId);
    }

    @Test
    @DisplayName("이미 등록된 사용자")
    public void testApplyForLectureRegistration_Failure_AlreadyRegistered() {
        // given: 이미 등록된 사용자라는 응답
        long lectureId = 1L;
        long userId = 123L;
        LectureRegistrationApplyResponseDto responseDto = new LectureRegistrationApplyResponseDto(false, "이미 신청한 사용자입니다.");

        // when: lectureRegistrationManager.apply 메서드가 호출되면 실패 응답을 반환하도록 설정
        when(lectureRegistrationManager.register(lectureId, userId)).thenReturn(responseDto);

        // then: 실패 응답이 제대로 반환되는지 확인
        LectureRegistrationApplyResponseDto result = lectureService.applyForLectureRegistration(lectureId, userId);

        assertFalse(result.success());
        assertEquals("이미 신청한 사용자입니다.", result.message());

        // verify: 의존성 메서드가 호출되었는지 확인
        verify(lectureRegistrationManager, times(1)).register(lectureId, userId);
    }

    @Test
    @DisplayName("신청 인원 초과")
    public void testApplyForLectureRegistration_Failure_ExceedCapacity() {
        // given: 강의 ID와 사용자 ID가 주어짐
        long lectureId = 1L;
        long userId = 123L;

        // 신청 인원 초과로 실패하는 응답
        LectureRegistrationApplyResponseDto responseDto = new LectureRegistrationApplyResponseDto(false, "신청 정원이 초과되었습니다.");

        // when: lectureRegistrationManager.register 메서드가 호출되면 신청 정원이 초과된 응답을 반환하도록 설정
        when(lectureRegistrationManager.register(lectureId, userId)).thenReturn(responseDto);

        // then: 실패 응답이 제대로 반환되는지 확인
        LectureRegistrationApplyResponseDto result = lectureService.applyForLectureRegistration(lectureId, userId);

        assertFalse(result.success());
        assertEquals("신청 정원이 초과되었습니다.", result.message());

        // verify: 의존성 메서드가 호출되었는지 확인
        verify(lectureRegistrationManager, times(1)).register(lectureId, userId);
    }



}