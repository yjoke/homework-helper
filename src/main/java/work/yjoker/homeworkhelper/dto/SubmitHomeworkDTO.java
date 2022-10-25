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

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("班级-学号-姓名")
    private String studentInfo;

    @ApiModelProperty("资源名字")
    private String resourceName;

    @ApiModelProperty("资源地址")
    private String resourceUrl;

    @ApiModelProperty("资源大小")
    private Integer resourceSize;

    /**
     * 添加 url 的公共前缀
     */
    public void refactorURL(String urlPrefix) {
        resourceUrl = urlPrefix + resourceUrl;
    }

    /**
     * 保存 info 字段
     */
    public void refactorInfo() {
        studentInfo = studentClazz + DASHED + studentNumber + DASHED + studentName;
    }

    public void refactorName() {
        resourceName = studentClazz + DASHED + studentNumber + DASHED + studentName + DASHED + resourceName;
    }

    private static final String DASHED = "-";
}
