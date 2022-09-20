package work.yjoker.homeworkhelper.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程信息
 */
@TableName(value ="course_info")
@Data
public class CourseInfo implements Serializable {
    /**
     * id, 独立的唯一标识
     */
    @TableId
    private Long id;

    /**
     * 创建人的 id
     */
    private Long createId;

    /**
     * 课程名字
     */
    private String courseName;

    /**
     * 课程封面图
     */
    private String courseImg;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModify;

    /**
     * 修改人的 id
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long idModify;

    /**
     * 是否删除标识
     */
    @TableLogic
    private Byte isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}