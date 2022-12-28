package me.univ.flex.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorService {

    private final ErrorRepository exceptionRepository;

    public void save(int statusCode, boolean solution, String reason, String info1, String info2, String info3, String info4, String info5, String trace) {
        ErrorEntity entity = ErrorEntity.builder()
            .statusCode(statusCode)
            .solution(solution)
            .reason(reason)
            .info1(info1)
            .info2(info2)
            .info3(info3)
            .info4(info4)
            .info5(info5)
            .trace(trace)
            .errorTime(TimestampUtil.now())
            .build();
        exceptionRepository.save(entity);
    }
}
