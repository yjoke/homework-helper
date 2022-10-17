package work.yjoker.homeworkhelper.dto;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.entity.AssignHomework;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private String title;

    @ApiModelProperty("作业的文本内容")
    private String content;

    @ApiModelProperty("作业的发布时间")
    private String gmtCreate;

    @ApiModelProperty("作业的截止时间")
    private String gmtExpire;

    /**
     * Entity to DTO
     */
    public static AssignHomeworkDTO toAssignHomeworkDTO(AssignHomework assignHomework) {
        AssignHomeworkDTO assignHomeworkDTO = BeanUtil.copyProperties(assignHomework, AssignHomeworkDTO.class);

        assignHomeworkDTO.setHomeworkId(assignHomework.getId());
        assignHomeworkDTO.setGmtCreate(format.format(assignHomework.getGmtCreate()));
        assignHomeworkDTO.setGmtExpire(format.format(assignHomework.getGmtExpire()));

        return assignHomeworkDTO;
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
