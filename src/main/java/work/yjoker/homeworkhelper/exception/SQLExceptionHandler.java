package work.yjoker.homeworkhelper.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import work.yjoker.homeworkhelper.dto.ApiResult;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author HeYunjia
 */
@Slf4j
@Order(10001)
@RestControllerAdvice
public class SQLExceptionHandler {

    /**
     * 处理数据库的完整性约束规范
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ApiResult<String> integrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e) {
        String errorMessage = e.getMessage();
        log.error("SQLIntegrityConstraintViolationException - {}", errorMessage);

        if (errorMessage.contains("Duplicate entry")) {
            String duplicateEntry = errorMessage
                    .substring(17, errorMessage.indexOf('\'', 17));
            return ApiResult.fail(duplicateEntry + " 已存在");
        }

        return ApiResult.error("数据未知错误, 服务器异常");
    }
}
