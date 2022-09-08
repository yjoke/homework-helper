package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.entity.LoginInfo;
import work.yjoker.homeworkhelper.service.LoginInfoService;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.vo.LoginInfoVO;

/**
 *
 */
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo>
    implements LoginInfoService{

    @Override
    public ApiResult<String> login(LoginInfoVO loginInfoVO) {
        return null;
    }

    @Override
    public ApiResult<String> register(LoginInfoVO loginInfoVO) {
        return null;
    }

    @Override
    public ApiResult<String> forget(LoginInfoVO loginInfoVO) {
        return null;
    }

    @Override
    public ApiResult<String> getCode() {
        return null;
    }
}




