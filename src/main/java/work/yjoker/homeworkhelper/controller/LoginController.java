package work.yjoker.homeworkhelper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.service.LoginInfoService;
import work.yjoker.homeworkhelper.vo.LoginInfoVO;

import javax.annotation.Resource;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@CrossOrigin("*")
@Api(tags = "登录相关")
public class LoginController {

    @Resource
    private LoginInfoService loginService;

    @PostMapping("login")
    @ApiOperation("登录验证")
    public ApiResult<String> login(@RequestBody LoginInfoVO loginInfoVO) {
        log.info("登录信息: {}", loginInfoVO);
        return loginService.login(loginInfoVO);
    }

    @PostMapping("register")
    @ApiOperation("注册新账号")
    public ApiResult<String> register(@RequestBody LoginInfoVO loginInfoVO) {
        log.info("注册信息为: {}", loginInfoVO);
        return loginService.register(loginInfoVO);
    }

    @PostMapping("forget")
    @ApiOperation("忘记密码")
    public ApiResult<String> forget(@RequestBody LoginInfoVO loginInfoVO) {
        log.info("要修改的手机号和密码: {}", loginInfoVO);
        return loginService.forget(loginInfoVO);
    }

    @PostMapping("code")
    @ApiOperation("获取手机验证码")
    public ApiResult<String> getCode(@RequestBody LoginInfoVO loginInfoVO) {
        return loginService.getCode(loginInfoVO);
    }
}


