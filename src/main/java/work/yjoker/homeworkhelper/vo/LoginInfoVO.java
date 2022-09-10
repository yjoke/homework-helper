package work.yjoker.homeworkhelper.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.entity.LoginInfo;

/**
 * @author HeYunjia
 */

@Data
@ApiModel("登录信息")
public class LoginInfoVO {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String code;

    /**
     * 将前端的登录数据映射为数据库数据
     *
     * @param salt 映射密码时的哈希盐
     * @return 返回数据库实体类 LoginInfo
     */
    public LoginInfo toLoginInfo(String salt) {

        LoginInfo loginInfo = BeanUtil.copyProperties(this, LoginInfo.class);

        String password = loginInfo.getPassword();
        password = DigestUtil.md5Hex(password + salt);
        loginInfo.setPassword(password);

        return loginInfo;
    }
}
