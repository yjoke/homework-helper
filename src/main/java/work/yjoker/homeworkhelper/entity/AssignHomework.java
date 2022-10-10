package work.yjoker.homeworkhelper.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 发布作业
 */
@TableName(value ="assign_homework")
@Data
public class AssignHomework implements Serializable {
    /**
     * id, 独立的唯一标识
     */
    @TableId
    private Long id;

    /**
     * 课程 id
     */
    private Long courseId;

    /**
     * 作业标题
     */
    private Long title;

    /**
     * 作业的文本内容
     */
    private String content;

    /**
     * 作业的截止时间
     */
    private Date gmtExpire;

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