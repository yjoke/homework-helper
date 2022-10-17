package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.AssignHomeworkDTO;
import work.yjoker.homeworkhelper.service.AssignHomeworkService;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.vo.AssignHomeworkVO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 * @author HeYunjia
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("assign")
@Api(tags = "发布作业相关")
public class AssignHomeworkController {

    @Resource
    private AssignHomeworkService assignHomeworkService;

    @PostMapping
    @ApiOperation("发布新作业")
    public ApiResult<AssignHomeworkDTO> assignHomework(@RequestBody AssignHomeworkVO assignHomeworkVO) {
        log.info("用户 {} 发布新作业 {}", Holder.get(PHONE_HOLDER), assignHomeworkVO);
        return assignHomeworkService.assignHomework(assignHomeworkVO);
    }

    @DeleteMapping("{homeworkId}")
    @ApiOperation("删除作业")
    public ApiResult<String> removeHomework(@PathVariable Long homeworkId) {
        log.info("用户 {} 删除作业 {}", Holder.get(PHONE_HOLDER), homeworkId);
        return assignHomeworkService.removeHomework(homeworkId);
    }

    @PutMapping("{homeworkId}")
    @ApiOperation("修改作业信息")
    public ApiResult<String> modifyHomework(
            @PathVariable Long homeworkId,
            @RequestBody AssignHomeworkVO assignHomeworkVO) {
        log.info("用户 {} 修改作业 {} 的信息为 {}", Holder.get(PHONE_HOLDER), homeworkId, assignHomeworkVO);
        return assignHomeworkService.modifyHomework(homeworkId, assignHomeworkVO);
    }

    @GetMapping("{courseId}")
    @ApiOperation("获取作业列表")
    public ApiResult<List<AssignHomeworkDTO>> assignList(@PathVariable Long courseId) {
        log.info("用户 {} 获取 {} 的作业列表", Holder.get(PHONE_HOLDER), courseId);
        return assignHomeworkService.assignList(courseId);
    }

    @PutMapping("close/{homeworkId}")
    @ApiOperation("截至提交作业")
    public ApiResult<String> closeSubmit(@PathVariable Long homeworkId) {
        log.info("用户 {} 截至 {} 的作业提交", Holder.get(PHONE_HOLDER), homeworkId);
        return assignHomeworkService.closeSubmit(homeworkId);
    }

    @PutMapping("open/{homeworkId}")
    @ApiOperation("开放作业提交")
    public ApiResult<String> openHomework(@PathVariable Long homeworkId,
                                             @RequestParam Date expireTime) {
        log.info("用户 {}, 开放 {} 的作业提交到 {}", Holder.get(PHONE_HOLDER), homeworkId, expireTime);
        return assignHomeworkService.openHomework(homeworkId, expireTime);
    }


}
