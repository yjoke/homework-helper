package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.entity.LoginInfo;
import work.yjoker.homeworkhelper.entity.UserInfo;
import work.yjoker.homeworkhelper.service.LoginInfoService;
import work.yjoker.homeworkhelper.service.UserInfoService;
import work.yjoker.homeworkhelper.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.Resource;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 *
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private LoginInfoService loginInfoService;

    @Override
    public ApiResult<UserInfoDTO> selfInfo() {

        String phone = Holder.get(PHONE_HOLDER);
        UserInfoDTO userInfoDTO = userInfoMapper.selectByPhone(phone);

        if (ObjectUtil.isNull(userInfoDTO)) return ApiResult.fail("请先完善信息");

        return ApiResult.success(userInfoDTO);
    }

    @Override
    public ApiResult<String> uploadInfo(UserInfoDTO userInfoDTO) {
        String phone = Holder.get(PHONE_HOLDER);

        if (!phone.equals(userInfoDTO.getPhone())) return ApiResult.fail("修改失败, 请不要恶意修改他人信息");

        LoginInfo loginInfo = loginInfoService.lambdaQuery()
                .eq(LoginInfo::getPhone, phone)
                .one();

        if (ObjectUtil.isNull(loginInfo)) return ApiResult.fail("接口异常, 未能识别当前登录用户");

        return saveOrUpdate(userInfoDTO.toUserInfo(loginInfo.getId()))
                ? ApiResult.success("信息上传成功")
                : ApiResult.fail("信息上传失败");
    }
}




