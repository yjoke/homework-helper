package work.yjoker.homeworkhelper.dto;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.entity.AssignHomework;
import work.yjoker.homeworkhelper.entity.SubmitHomework;

import java.text.SimpleDateFormat;
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

    @ApiModelProperty("是否已经截止")
    private boolean isExpired;

    @ApiModelProperty("是否提交")
    private boolean isSubmitted;

    @ApiModelProperty("作业文件名")
    private String homeworkFileName;

    @ApiModelProperty("作业地址")
    private String homeworkFileUrl;

    /**
     * 判断作业是否提交
     *
     * @return 返回 this
     */
    public AssignHomeworkDTO setHomework(SubmitHomework submitHomework) {
        if (submitHomework == null) {
            this.setSubmitted(false);
        } else {
            this.setSubmitted(true);
            this.setHomeworkFileName(submitHomework.getResourceName());
            this.setHomeworkFileUrl(submitHomework.getResourceUrl());
        }

        return this;
    }

    /**
     * Entity to DTO
     */
    public static AssignHomeworkDTO toAssignHomeworkDTO(AssignHomework assignHomework) {
        AssignHomeworkDTO assignHomeworkDTO = BeanUtil.copyProperties(assignHomework, AssignHomeworkDTO.class);

        assignHomeworkDTO.setHomeworkId(assignHomework.getId());
        assignHomeworkDTO.setGmtCreate(format.format(assignHomework.getGmtCreate()));
        assignHomeworkDTO.setGmtExpire(format.format(assignHomework.getGmtExpire()));
        assignHomeworkDTO.setExpired(assignHomework.getGmtExpire().getTime() - System.currentTimeMillis() <= 0);

        return assignHomeworkDTO;
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
