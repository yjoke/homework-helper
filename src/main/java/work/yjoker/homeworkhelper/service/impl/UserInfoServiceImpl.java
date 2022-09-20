package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.Header;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import work.yjoker.homeworkhelper.common.JWTUtil;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.entity.LoginInfo;
import work.yjoker.homeworkhelper.entity.UserInfo;
import work.yjoker.homeworkhelper.service.LoginInfoService;
import work.yjoker.homeworkhelper.service.UserInfoService;
import work.yjoker.homeworkhelper.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.vo.LoginInfoVO;

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

    @Resource
    private OssWrapper ossWrapper;

    @Value("${homework-helper.login.secret}")
    private String secret;

    @Override
    public ApiResult<UserInfoDTO> selfInfo() {

        String phone = Holder.get(PHONE_HOLDER);
        UserInfoDTO userInfoDTO = userInfoMapper.selectDTOByPhone(phone);

        if (ObjectUtil.isNull(userInfoDTO)) return ApiResult.fail("请先完善信息");

        userInfoDTO.setAvatar(ossWrapper.getUrlPrefix() + userInfoDTO.getAvatar());

        return ApiResult.success(userInfoDTO);
    }

    @Override
    public ApiResult<String> uploadInfo(UserInfoDTO userInfoDTO) {
        String phone = Holder.get(PHONE_HOLDER);

        LoginInfo loginInfo = loginInfoService.lambdaQuery()
                .eq(LoginInfo::getPhone, phone)
                .one();

        if (ObjectUtil.isNull(loginInfo)) return ApiResult.fail("接口异常, 未能识别当前登录用户");

        return saveOrUpdate(userInfoDTO.toUserInfo(loginInfo.getId(), ossWrapper.getUrlPrefix().length()))
                ? ApiResult.success("信息上传成功")
                : ApiResult.fail("信息上传失败");
    }

    @Override
    public ApiResult<String> modifyPhone(LoginInfoVO loginInfoVO) {

        String phone = Holder.get(PHONE_HOLDER);
        String newPhone = loginInfoVO.getPhone();
        if (phone.equals(newPhone)) return ApiResult.fail("修改失败, 当前已经绑定该手机号");

        LoginInfo loginInfo = loginInfoService.lambdaQuery()
                .eq(LoginInfo::getPhone, newPhone)
                .one();

        if (ObjectUtil.isNotNull(loginInfo)) return ApiResult.fail("修改失败, 手机号已被注册, 请先注销该账户");

        loginInfo = loginInfoService.lambdaQuery()
                .eq(LoginInfo::getPhone, phone)
                .one();

        loginInfo.setPhone(newPhone);

        if (loginInfoService.updateById(loginInfo)) {
            String token = JWTUtil.create(loginInfoVO.getPhone());

            return ApiResult.success(Header.AUTHORIZATION.toString(), token);
        } else {
            return ApiResult.fail("手机号修改失败");
        }
    }

    @Override
    public ApiResult<String> modifyPassword(LoginInfoVO loginInfoVO) {

        String phone = Holder.get(PHONE_HOLDER);

        LoginInfo one = loginInfoService.lambdaQuery()
                .select(LoginInfo::getId)
                .eq(LoginInfo::getPhone, phone)
                .one();

        LoginInfo loginInfo = loginInfoVO.toLoginInfo(secret);

        one.setPassword(loginInfo.getPassword());

        return loginInfoService.updateById(one)
                ? ApiResult.success("密码修改成功")
                : ApiResult.fail("密码修改失败");
    }
}




