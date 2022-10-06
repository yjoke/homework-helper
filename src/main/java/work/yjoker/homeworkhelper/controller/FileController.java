package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.service.FileService;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.Resource;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 * @author HeYunjia
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("upload")
@Api(tags = "文件相关")
// TODO 现在的文件上传存在一点问题, 可以使用 token 信息进行任意上传服务器
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("avatar")
    @ApiOperation("用户上传头像")
    public ApiResult<String> saveAvatar(@RequestParam("avatar") MultipartFile file) {
        log.info("手机号为 {} 的用户上传头像", Holder.get(PHONE_HOLDER));
        return fileService.saveAvatar(file);
    }

    @PostMapping("cover")
    @ApiOperation("上传课程封面")
    public ApiResult<String> saveCover(@RequestParam("cover") MultipartFile file) {
        log.info("手机号位 {} 的用户上传封面头像", Holder.get(PHONE_HOLDER));
        return fileService.saveCover(file);
    }

    @PostMapping("resource")
    @ApiOperation("上传课程资源")
    public ApiResult<String> saveCourseResource(@RequestParam("resource") MultipartFile file) {
        log.info("手机号为 {} 的用户上传课程资源", Holder.get(PHONE_HOLDER));
        return fileService.saveCourseResource(file);
    }

    @PostMapping("homework")
    @ApiOperation("上传作业文件")
    public ApiResult<String> saveHomework(@RequestParam("homework") MultipartFile file) {
        log.info("手机号为 {} 的用户上传课程资源", Holder.get(PHONE_HOLDER));
        return fileService.saveHomework(file);
    }

}
