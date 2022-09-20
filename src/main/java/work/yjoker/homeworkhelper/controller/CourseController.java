package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.CourseInfoDTO;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.Resource;

import java.util.List;

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
    public ApiResult<String> saveCourse(@RequestBody CourseInfoDTO courseInfoDTO) {
        log.info("手机号为 {} 的用户创建新课程 {}", Holder.get(PHONE_HOLDER), courseInfoDTO);
        return courseInfoService.saveCourse(courseInfoDTO);
    }

    @GetMapping("created")
    @ApiOperation("获取自己创建的课程")
    public ApiResult<List<CourseInfoDTO>> created() {
        log.info("手机号为 {} 的用户获取自己创建的课程列表", Holder.get(PHONE_HOLDER));
        return courseInfoService.created();
    }

    @GetMapping("added")
    @ApiOperation("获取自己加入的课程")
    public ApiResult<List<CourseInfoDTO>> added() {
        return ApiResult.error("接口未实现");
    }
}
