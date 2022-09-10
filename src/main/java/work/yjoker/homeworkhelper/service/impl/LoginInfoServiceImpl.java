package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.Header;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import work.yjoker.homeworkhelper.common.JWTUtil;
import work.yjoker.homeworkhelper.common.SMSUtil;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.entity.LoginInfo;
import work.yjoker.homeworkhelper.service.LoginInfoService;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.vo.LoginInfoVO;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static work.yjoker.homeworkhelper.constant.RedisConstants.CODE_PREFIX;
import static work.yjoker.homeworkhelper.constant.RedisConstants.PROJECT_PREFIX;

/**
 *
 */

@Slf4j
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo>
    implements LoginInfoService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${homework-helper.login.secret}")
    private String secret;

    private static final String CODE_KEY = PROJECT_PREFIX + CODE_PREFIX;

    private static final int DEFAULT_CODE_LEN = 6;
    private static final int DEFAULT_CODE_EXPIRE = 2;

    @Override
    public ApiResult<String> login(LoginInfoVO loginInfoVO) {
        LoginInfo loginInfo = loginInfoVO.toLoginInfo(secret);
        LoginInfo one = lambdaQuery()
                .eq(LoginInfo::getPhone, loginInfo.getPhone())
                .eq(LoginInfo::getPassword, loginInfo.getPassword())
                .one();

        if (ObjectUtil.isNull(one)) ApiResult.fail("账号或密码错误");

        String token = JWTUtil.create(loginInfoVO.getPhone());

        return ApiResult.success(Header.AUTHORIZATION.toString(), token);
    }

    @Override
    public ApiResult<String> register(LoginInfoVO loginInfoVO) {

        if (errorCode(loginInfoVO)) return ApiResult.fail("验证码错误");

        return save(loginInfoVO.toLoginInfo(secret))
                ? ApiResult.success("注册成功")
                : ApiResult.fail("注册失败");
    }

    @Override
    public ApiResult<String> forget(LoginInfoVO loginInfoVO) {

        if (errorCode(loginInfoVO)) return ApiResult.fail("验证码错误");

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
        String key = CODE_KEY + phone;
        if (BooleanUtil.isTrue(stringRedisTemplate.hasKey(key))) {
            return ApiResult.fail("发送短信频繁, 请稍后重试");
        }

        String code = RandomUtil.randomNumbers(DEFAULT_CODE_LEN);
        if (SMSUtil.sendMessage(phone, code, DEFAULT_CODE_EXPIRE)) {
            stringRedisTemplate.opsForValue()
                    .set(key, code, DEFAULT_CODE_EXPIRE, TimeUnit.MINUTES);

            return ApiResult.success("短信发送成功, 请注意查收");
        } else {
            log.error("短信发送失败: {}", loginInfoVO);
            return ApiResult.fail("短信发送失败, 请稍后重试");
        }
    }

    /**
     * 校验验证码是否非法
     *
     * @param loginInfoVO 登录/注册信息
     * @return 返回是否非法
     */
    private boolean errorCode(LoginInfoVO loginInfoVO) {

        String phone = loginInfoVO.getPhone();
        if (StrUtil.isEmpty(phone)) return false;

        String code = loginInfoVO.getCode();
        if (StrUtil.isEmpty(code)) return false;

        String key = CODE_KEY + phone;
        String correct = stringRedisTemplate.opsForValue().get(key);

        return !code.equals(correct);
    }

}




