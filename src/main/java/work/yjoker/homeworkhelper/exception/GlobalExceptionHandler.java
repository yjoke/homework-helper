package work.yjoker.homeworkhelper.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import work.yjoker.homeworkhelper.dto.ApiResult;

/**
 * 处理全局异常
 *
 * @author HeYunjia
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResult<String> allException(Exception e) {
        log.info(e.getMessage());
        return ApiResult.error();
    }
}

