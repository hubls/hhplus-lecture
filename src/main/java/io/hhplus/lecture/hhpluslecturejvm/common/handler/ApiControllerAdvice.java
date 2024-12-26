package io.hhplus.lecture.hhpluslecturejvm.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ApiControllerAdvice {
    // 서버 에러
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse("500", "에러가 발생했습니다."));
    }

    // 숫자의 범위가 잘못 되었을 때
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("400", e.getMessage()));
    }

    // 컨트롤러에 잘못된 인자값이 들어왔을 때
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = String.format("'%s'의 값 '%s'은 잘못된 요청 값입니다.",
                e.getName(), e.getValue());
        return ResponseEntity.badRequest().body(new ErrorResponse("400", message));
    }
}
