package work.yjoker.homeworkhelper.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
}
