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
@Api(tags = "用户信息相关")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("avatar")
    @ApiOperation("用户上传头像")
    public ApiResult<String> saveAvatar(@RequestParam("avatar") MultipartFile file) {
        log.info("手机号为 {} 的用户上传头像", Holder.get(PHONE_HOLDER));
        return fileService.saveAvatar(file);
    }

}
