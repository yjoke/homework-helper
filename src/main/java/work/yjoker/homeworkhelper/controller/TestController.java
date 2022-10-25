package work.yjoker.homeworkhelper.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;

/**
 * @author HeYunjia
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("test")
public class TestController {

    @Resource
    private OssWrapper ossWrapper;

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

    @GetMapping("download")
    public ResponseEntity<InputStreamResource> download() {

        InputStream inputStream = ossWrapper.downFile("max-2MB/15c1a6f7-1b2a-4171-ac68-831c5495b0e5.jpg");

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(inputStream));
    }
}
