package work.yjoker.homeworkhelper.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程的学生信息
 */
@TableName(value ="select_course")
@Data
public class SelectCourse implements Serializable {
    /**
     * id, 独立的唯一标识
     */
    @TableId
    private Long id;

    /**
     * 课程 id, 来自 class 表, 有普通索引
     */
    private Long courseId;

    /**
     * 学生 id, 来自 login 表, 有普通索引
     */
    private Long studentId;

    /**
     * 学生成绩
     */
    private Byte grade;

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