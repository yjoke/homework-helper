package work.yjoker.homeworkhelper.service;

import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import work.yjoker.homeworkhelper.vo.LoginInfoVO;

/**
 *
 */
public interface UserInfoService extends IService<UserInfo> {

    ApiResult<UserInfoDTO> selfInfo();

    ApiResult<String> uploadInfo(UserInfoDTO userInfoDTO);

    ApiResult<String> modifyPhone(LoginInfoVO loginInfoVO);

    ApiResult<String> modifyPassword(LoginInfoVO loginInfoVO);
}
