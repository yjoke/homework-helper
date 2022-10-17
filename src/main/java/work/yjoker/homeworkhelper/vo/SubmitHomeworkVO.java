package work.yjoker.homeworkhelper.vo;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.entity.SubmitHomework;

/**
 * @author HeYunjia
 */
@Data
@ApiModel("提交作业")
public class SubmitHomeworkVO {

    @ApiModelProperty("提交的作业的 id")
    private Long assignId;

    @ApiModelProperty("资源名字")
    private String resourceName;

    @ApiModelProperty("资源地址")
    private String resourceUrl;

    @ApiModelProperty("资源大小")
    private Integer resourceSize;

    /**
     * VO to Entity
     *
     * @param studentId 学生的 id
     * @param urlPrefixLength 资源前缀长度
     */
    public SubmitHomework toSubmitHomework(Long studentId, int urlPrefixLength) {
        SubmitHomework submitHomework = BeanUtil.copyProperties(this, SubmitHomework.class);

        submitHomework.setStudentId(studentId);
        submitHomework.setResourceUrl(resourceUrl.substring(urlPrefixLength));

        return submitHomework;
    }
}
