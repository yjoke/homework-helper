package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.entity.SubmitHomework;
import work.yjoker.homeworkhelper.service.SubmitHomeworkService;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("submit")
@Api(tags = "提交作业相关")
public class SubmitHomeworkController {

    @Resource
    private SubmitHomeworkService submitHomeworkService;

    @GetMapping("{homeworkId}")
    @ApiOperation("作业提交的列表")
    public ApiResult<Object> submitList(@PathVariable Long homeworkId) {
        return null;
    }

    @PostMapping
    @ApiOperation("提交作业")
    public ApiResult<String> submitHomework(@RequestBody Object Object) {
        return null;
    }
}
