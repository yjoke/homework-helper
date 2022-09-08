package work.yjoker.homeworkhelper.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
}
