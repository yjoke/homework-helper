package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.CourseResourceDTO;
import work.yjoker.homeworkhelper.service.CourseResourceService;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.vo.CourseResourceVO;

import javax.annotation.Resource;
import java.util.List;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 * @author HeYunjia
 */
@Slf4j
@RestController
@CrossOrigin("*")
@Api(tags = "课程资源")
@RequestMapping("course-resource")
public class CourseResourceController {

    @Resource
    private CourseResourceService courseResourceService;

    @PostMapping
    @ApiOperation("上传资源信息")  // TODO 没有做测试
    public ApiResult<String> saveCourseResource(CourseResourceVO courseResourceVO) {
        log.info("用户 {} 上传资源信息", Holder.get(PHONE_HOLDER));
        return courseResourceService.saveCourseResource(courseResourceVO);
    }

    @GetMapping("{courseId}")
    @ApiOperation("获取资源列表")  // TODO 没有做测试
    public ApiResult<List<CourseResourceDTO>> dtoList(@PathVariable Long courseId) {
        log.info("用户 {} 获取资源列表", Holder.get(PHONE_HOLDER));
        return courseResourceService.dtoList(courseId);
    }
}
