package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.entity.CourseInfo;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.Resource;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 * @author HeYunjia
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("course")
@Api(tags = "课程信息相关")
public class CourseController {

    @Resource
    private CourseInfoService courseInfoService;

    @PostMapping
    @ApiOperation("创建新课程")
    public ApiResult<String> saveCourse(@RequestBody CourseInfo courseInfo) {
        log.info("手机号为 {} 的用户创建新课程 {}", Holder.get(PHONE_HOLDER), courseInfo);
        return courseInfoService.saveCourse(courseInfo);
    }
}
