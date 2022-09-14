package work.yjoker.homeworkhelper.dto;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.common.wrapper.SmsWrapper;
import work.yjoker.homeworkhelper.entity.UserInfo;

/**
 * @author HeYunjia
 */
@Data
@ApiModel("用户信息")
public class UserInfoDTO {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("班级")
    private String clazz;

    @ApiModelProperty("学号")
    private String number;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 映射为 UserInfo
     *
     * @param id 查询得到的该数据的所属 id
     * @param urlPrefixLength avatarUrl 的公共前缀长度
     * @return 返回 UserInfo
     */
    public UserInfo toUserInfo(Long id, int urlPrefixLength) {
        UserInfo userInfo = BeanUtil.copyProperties(this, UserInfo.class);

        userInfo.setId(id);
        String avatar = userInfo.getAvatar();
        avatar = avatar.substring(urlPrefixLength);
        userInfo.setAvatar(avatar);

        return userInfo;
    }
}
