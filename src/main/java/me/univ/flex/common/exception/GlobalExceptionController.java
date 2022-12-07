package me.univ.flex.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController {

    @ExceptionHandler({
        RuntimeException.class
    })
    public ResponseEntity<Object> BadRequestException(final RuntimeException ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
