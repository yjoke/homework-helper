package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.Header;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import work.yjoker.homeworkhelper.common.JWTUtil;
import work.yjoker.homeworkhelper.common.wrapper.SmsWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.entity.LoginInfo;
import work.yjoker.homeworkhelper.service.LoginInfoService;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.vo.LoginInfoVO;

import javax.annotation.Resource;

/**
 *
 */

@Slf4j
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo>
    implements LoginInfoService{

    @Resource
    private SmsWrapper smsWrapper;

    @Value("${homework-helper.login.secret}")
    private String secret;

    private static final byte NOT_IS_BLACK = 0;

    @Override
    public ApiResult<String> login(LoginInfoVO loginInfoVO) {
        LoginInfo loginInfo = loginInfoVO.toLoginInfo(secret);
        LoginInfo one = lambdaQuery()
                .eq(LoginInfo::getPhone, loginInfo.getPhone())
                .eq(LoginInfo::getPassword, loginInfo.getPassword())
                .eq(LoginInfo::getIsBlacklist, NOT_IS_BLACK)
                .one();

        if (ObjectUtil.isNull(one)) return ApiResult.fail("账号或密码错误");

        String token = JWTUtil.create(loginInfoVO.getPhone());

        return ApiResult.success(Header.AUTHORIZATION.toString(), token);
    }

    @Override
    public ApiResult<String> register(LoginInfoVO loginInfoVO) {

        if (smsWrapper.verifyCode(loginInfoVO.getPhone(), loginInfoVO.getCode())) {
            return save(loginInfoVO.toLoginInfo(secret))
                    ? ApiResult.success("注册成功")
                    : ApiResult.fail("注册失败");
        }

        return ApiResult.fail("验证码错误");
    }

    @Override
    public ApiResult<String> forget(LoginInfoVO loginInfoVO) {

        if (!smsWrapper.verifyCode(loginInfoVO.getPhone(), loginInfoVO.getCode())) {
            return ApiResult.fail("验证码错误");
        }

        LoginInfo loginInfo = lambdaQuery()
                .eq(LoginInfo::getPhone, loginInfoVO.getPhone())
                .one();

        if (ObjectUtil.isNull(loginInfo)) return ApiResult.fail("账号不存在");

        String newPassword = DigestUtil.md5Hex(loginInfoVO.getPassword() + secret);
        loginInfo.setPassword(newPassword);

        return updateById(loginInfo)
                ? ApiResult.success("找回成功")
                : ApiResult.fail("找回失败");
    }

    @Override
    public ApiResult<String> getCode(LoginInfoVO loginInfoVO) {

        String phone = loginInfoVO.getPhone();
        if (smsWrapper.hasCode(phone)) {
            return ApiResult.fail("发送短信频繁, 请稍后重试");
        }

        if (smsWrapper.sendMessage(phone)) {
            return ApiResult.success("短信发送成功, 请注意查收");
        } else {
            log.error("短信发送失败: {}", loginInfoVO);
            return ApiResult.fail("短信发送失败, 请稍后重试");
        }
    }
}




