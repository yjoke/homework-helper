package work.yjoker.homeworkhelper.mapper;

import work.yjoker.homeworkhelper.entity.LoginInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * work.yjoker.homeworkhelper.entity.LoginInfo
 */
public interface LoginInfoMapper extends BaseMapper<LoginInfo> {

    Long selectIdByPhone(String phone);
}




