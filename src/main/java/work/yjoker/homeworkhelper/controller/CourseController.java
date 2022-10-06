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

    @PostMapping("created")
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

    @GetMapping("{id}")
    @ApiOperation("获取课程信息")
    public ApiResult<CourseInfoDTO> courseInfoDTO(@PathVariable Long id) {
        log.info("用户 {} 获取课程 {} 的信息", Holder.get(PHONE_HOLDER), id);
        return courseInfoService.courseInfoDTO(id);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除课程信息")
    public ApiResult<String> removeCourse(@PathVariable Long id) {
        log.info("用户 {} 删除课程 {} 的信息", Holder.get(PHONE_HOLDER), id);
        // TODO 删除课程接口, 需要同时删除学生的添加信息 等, 后续完善
        return ApiResult.fail("接口还未实现");
    }

    @PutMapping
    @ApiOperation("修改课程信息")
    public ApiResult<String> modifyCourseInfo(@RequestBody CourseInfoDTO courseInfoDTO) {
        log.info("用户 {} 修改课程信息为 {}", Holder.get(PHONE_HOLDER), courseInfoDTO);
        return courseInfoService.modifyCourseInfo(courseInfoDTO);
    }

    @GetMapping("code/{id}")
    @ApiOperation("获取课程邀请码")
    public ApiResult<String> code(@PathVariable Long id) {
        log.info("用户 {} 获取课程 {} 的邀请码", Holder.get(PHONE_HOLDER), id);
        return courseInfoService.code(id);
    }

    @PutMapping("code/{id}")
    @ApiOperation("生成课程邀请码")
    public ApiResult<String> modifyCode(@PathVariable Long id) {
        log.info("用户 {} 获取课程 {} 的邀请码", Holder.get(PHONE_HOLDER), id);
        return courseInfoService.modifyCode(id);
    }

    @GetMapping("added")
    @ApiOperation("获取自己加入的课程")
    public ApiResult<List<CourseInfoDTO>> added() {
        log.info("用户 {} 获取自己已加入课程", Holder.get(PHONE_HOLDER));
        return courseInfoService.added();
    }

    @PostMapping("added/{code}")
    @ApiOperation("加入课程")
    public ApiResult<CourseInfoDTO> addedCourse(@PathVariable String code) {
        log.info("用户 {} 加入课程 {}", Holder.get(PHONE_HOLDER), code);
        return courseInfoService.addedCourse(code);
    }

}
