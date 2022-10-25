package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.SubmitHomeworkDTO;
import work.yjoker.homeworkhelper.service.SubmitHomeworkService;
import work.yjoker.homeworkhelper.vo.SubmitHomeworkVO;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping
    @ApiOperation("提交作业")
    public ApiResult<String> submitHomework(@RequestBody SubmitHomeworkVO submitHomeworkVO) {
        return submitHomeworkService.submitHomework(submitHomeworkVO);
    }

    @GetMapping("has/{homeworkId}")
    @ApiOperation("已提交作业的学生列表")
    public ApiResult<List<SubmitHomeworkDTO>> submittedList(@PathVariable Long homeworkId) {
        return submitHomeworkService.submittedList(homeworkId);
    }

    @GetMapping("not/{homeworkId}")
    @ApiOperation("未提交作业的学生列表")
    public ApiResult<List<SubmitHomeworkDTO>> notSubmittedList(@PathVariable Long homeworkId) {
        return submitHomeworkService.notSubmittedList(homeworkId);
    }

    @GetMapping("{homeworkId}")
    @ApiOperation("下载作业的压缩文件")
    public ApiResult<String> zipFile(@PathVariable Long homeworkId) {
        return submitHomeworkService.zipFile(homeworkId);
    }
}
