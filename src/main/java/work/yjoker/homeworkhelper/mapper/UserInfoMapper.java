package work.yjoker.homeworkhelper.mapper;

import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * work.yjoker.homeworkhelper.entity.UserInfo
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfoDTO selectByPhone(String phone);
}




