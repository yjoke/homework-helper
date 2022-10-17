package work.yjoker.homeworkhelper.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author HeYunjia
 */
@Data
@ApiModel("作业提交信息")
public class SubmitHomeworkDTO {

    @ApiModelProperty("学生班级")
    private String studentClazz;

    @ApiModelProperty("学生学号")
    private String studentNumber;

    @ApiModelProperty("学生名字")
    private String studentName;

    @ApiModelProperty("资源名字")
    private String resourceName;

    @ApiModelProperty("资源地址")
    private String resourceUrl;

    @ApiModelProperty("资源大小")
    private Integer resourceSize;
}
