package work.yjoker.homeworkhelper.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import work.yjoker.homeworkhelper.dto.ApiResult;


/**
 * 处理全局异常
 *
 * @author HeYunjia
 */

@Slf4j
@Order(10002)
@RestControllerAdvice
public class OtherExceptionHandler {

    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<String> exceptionHandler(Exception e) {
        log.info(e.getMessage());
        return ApiResult.error("服务器发生未知异常");
    }
}

