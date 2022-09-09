package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 *
 */

@Slf4j
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo>
    implements LoginInfoService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ApiResult<String> login(LoginInfoVO loginInfoVO) {
        // TODO 这里应该要添加查询详细信息, 成功后作为返回

        return ApiResult.fail();
    }

    @Override
    public ApiResult<String> register(LoginInfoVO loginInfoVO) {




        return ApiResult.error();
    }

    @Override
    public ApiResult<String> forget(LoginInfoVO loginInfoVO) {
        return ApiResult.error();
    }

    @Override
    public ApiResult<String> getCode(LoginInfoVO loginInfoVO) {

        String phone = loginInfoVO.getPhone();
        if (StrUtil.isEmpty(phone)) return ApiResult.error("手机号不能为空");

        String tag = "verify:code:";
        String key = tag + phone;
        if (BooleanUtil.isTrue(stringRedisTemplate.hasKey(key))) {
            return ApiResult.fail("发送短信频繁, 请稍后重试");
        }

        String code = RandomUtil.randomNumbers(6);
        int expire = 2;
        if (SMSUtil.sendMessage(phone, code, expire)) {
            stringRedisTemplate.opsForValue()
                    .set(key, code, expire, TimeUnit.MINUTES);

            return ApiResult.success("短信发送成功, 请注意查收");
        } else {
            return ApiResult.fail("服务器异常, 请稍后重试");
        }
    }
}




