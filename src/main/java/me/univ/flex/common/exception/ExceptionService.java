package me.univ.flex.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.exception.ExceptionEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExceptionService {

    private final ExceptionRepository exceptionRepository;

    @Async
    public void save(String type, String message, String stackTrace) {
        ExceptionEntity entity = ExceptionEntity.builder()
            .exceptionType(type)
            .message(message)
            .stackTrace(stackTrace)
            .errorTime(TimestampUtil.now())
            .build();

        exceptionRepository.save(entity);
    }
}
