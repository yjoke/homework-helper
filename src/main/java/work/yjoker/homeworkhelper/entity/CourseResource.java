package work.yjoker.homeworkhelper.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程资源
 * @TableName course_resource
 */
@TableName(value ="course_resource")
@Data
public class CourseResource implements Serializable {
    /**
     * id, 独立的唯一标识
     */
    @TableId
    private Long id;

    /**
     * 课程 id, 来自 course 表, 有普通索引
     */
    private Long courseId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源地址
     */
    private String resourceUrl;

    /**
     * 资源大小
     */
    private Integer resourceSize;

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