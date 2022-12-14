package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.service.UserInfoService;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.vo.LoginInfoVO;

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
    @ApiOperation("用户获取个人信息")
    public ApiResult<UserInfoDTO> selfInfo() {
        log.info("手机号为 {} 的用户请求个人信息", Holder.get(PHONE_HOLDER));
        return userInfoService.selfInfo();
    }

    @PostMapping
    @ApiOperation("用户上传个人信息")
    public ApiResult<String> uploadInfo(@RequestBody UserInfoDTO userInfoDTO) {
        log.info("手机号为 {} 的用户上传个人信息: {}", Holder.get(PHONE_HOLDER), userInfoDTO);
        return userInfoService.uploadInfo(userInfoDTO);
    }

    // TODO 这里修改应该是 PUT, 前端也没来得及改
    @PostMapping("modify-phone")
    @ApiOperation("手机号换绑")
    public ApiResult<String> modifyPhone(@RequestBody LoginInfoVO loginInfoVO) {
        log.info("手机号换绑: {}", loginInfoVO);
        return userInfoService.modifyPhone(loginInfoVO);
    }

    // TODO 这里修改应该是 PUT, 前端也没来得及改
    @PostMapping("modify-password")
    @ApiOperation("修改密码")
    public ApiResult<String> modifyPassword(@RequestBody LoginInfoVO loginInfoVO) {
        log.info("修改密码: {}", loginInfoVO);
        return userInfoService.modifyPassword(loginInfoVO);
    }

    // TODO 这里弄成 DELETE 比较好
    @PostMapping("logout")
    @ApiOperation(value = "注销账户", notes = "此接口是用户注销自己的账户, 不是退出登录, 删除账户的后台数据")
    public ApiResult<String> logout(@RequestBody LoginInfoVO loginInfoVO) {
        log.info("注销账户: {}", loginInfoVO);
        // TODO 注销账户接口
        return ApiResult.fail("接口还未实现");
    }

}
