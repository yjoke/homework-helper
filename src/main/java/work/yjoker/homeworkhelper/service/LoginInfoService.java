package work.yjoker.homeworkhelper.service;

import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.entity.LoginInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import work.yjoker.homeworkhelper.vo.LoginInfoVO;

/**
 *
 */
public interface LoginInfoService extends IService<LoginInfo> {

    ApiResult<String> login(LoginInfoVO loginInfoVO);

    ApiResult<String> register(LoginInfoVO loginInfoVO);

    ApiResult<String> forget(LoginInfoVO loginInfoVO);

    ApiResult<String> getCode(LoginInfoVO loginInfoVO);
}
