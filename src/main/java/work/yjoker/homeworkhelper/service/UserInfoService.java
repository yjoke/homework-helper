package work.yjoker.homeworkhelper.service;

import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserInfoService extends IService<UserInfo> {

    ApiResult<UserInfoDTO> selfInfo();

    ApiResult<String> uploadInfo(UserInfoDTO userInfoDTO);
}
