package work.yjoker.homeworkhelper.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 提交作业
 */
@TableName(value ="submit_homework")
@Data
public class SubmitHomework implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 提交的作业的 id
     */
    private Long assignId;

    /**
     * 提交的学生
     */
    private Long studentId;

    /**
     * 资源名字
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