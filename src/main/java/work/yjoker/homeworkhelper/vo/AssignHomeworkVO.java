package work.yjoker.homeworkhelper.vo;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.entity.AssignHomework;

import java.util.Date;

/**
 * @author HeYunjia
 */
@Data
@ApiModel("接收的作业数据")
public class AssignHomeworkVO {

    @ApiModelProperty("课程 id")
    private Long courseId;

    @ApiModelProperty("作业标题")
    private Long title;

    @ApiModelProperty("作业的文本内容")
    private String content;

    @ApiModelProperty("作业截止时间")
    private Date gmtExpire;

    /**
     * VO to Entity
     */
    public AssignHomework toAssignHomework() {
        return BeanUtil.copyProperties(this, AssignHomework.class);
    }
}
