package work.yjoker.homeworkhelper.dto;

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
@ApiModel("返回的作业数据")
public class AssignHomeworkDTO {

    @ApiModelProperty("作业 id")
    private Long homeworkId;

    @ApiModelProperty("作业标题")
    private Long title;

    @ApiModelProperty("作业的文本内容")
    private String content;

    @ApiModelProperty("作业的发布时间")
    private Date gmtCreate;

    @ApiModelProperty("作业的截止时间")
    private Date gmtExpire;

    /**
     * Entity to DTO
     */
    public static AssignHomeworkDTO toAssignHomeworkDTO(AssignHomework assignHomework) {
        return BeanUtil.copyProperties(assignHomework, AssignHomeworkDTO.class);
    }
}
