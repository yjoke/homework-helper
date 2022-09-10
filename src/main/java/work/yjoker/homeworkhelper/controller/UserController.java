package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.service.UserInfoService;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.Resource;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("user")
@Api(tags = "用户信息相关")
public class UserController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping
    @ApiOperation("登录用户获取个人信息")
    public ApiResult<UserInfoDTO> selfInfo() {
        log.info("手机号为 {} 的用户请求个人信息", Holder.get(PHONE_HOLDER));
        return userInfoService.selfInfo();
    }
}
