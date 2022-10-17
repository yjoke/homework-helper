package work.yjoker.homeworkhelper.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.Map;

/**
 * @author HeYunjia
 */
@Slf4j
@RestController
@CrossOrigin("*")
public class TestController {

    @Data
    static class test {
        String title;
        String content;
        Date gmtExpire;
    }

    @PostMapping("test")
    public ApiResult<String> test(@RequestBody test map){
        log.info("test 检测数据: {}", map);
        return ApiResult.success("success");
    }
}
