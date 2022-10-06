package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.service.SelectCourseService;
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
@RequestMapping("select")
@Api(tags = "选课信息相关")
public class SelectCourseController {

    @Resource
    private SelectCourseService selectCourseService;

    // TODO 获取自己的选课表, 现在在 CourseController 中, 回头用空也改过来

    @GetMapping("student-list/{courseId}")
    @ApiOperation("课程的学生列表")
    public ApiResult<List<UserInfoDTO>> studentList(@PathVariable Long courseId) {
        log.info("用户 {} 获取课程 {} 的学生信息", Holder.get(PHONE_HOLDER), courseId);
        return selectCourseService.studentList(courseId);
    }

}
