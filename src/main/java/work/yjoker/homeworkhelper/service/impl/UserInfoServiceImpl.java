package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.entity.UserInfo;
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

    @Override
    public ApiResult<UserInfoDTO> selfInfo() {

        String phone = Holder.get(PHONE_HOLDER);
        UserInfoDTO userInfoDTO = userInfoMapper.selectByPhone(phone);

        if (ObjectUtil.isNull(userInfoDTO)) return ApiResult.fail("请先完善信息");

        return ApiResult.success(userInfoDTO);
    }
}




