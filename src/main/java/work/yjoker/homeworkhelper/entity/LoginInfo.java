package work.yjoker.homeworkhelper.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 保存登录相关的信息
 * @TableName login_info
 */
@TableName(value ="login_info")
@Data
public class LoginInfo implements Serializable {
    /**
     * id, 独立的唯一标识
     */
    @TableId
    private Long id;

    /**
     * 手机号, 唯一标识
     */
    private String phone;

    /**
     * 密码, 哈希摘要保存
     */
    private String password;

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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long idModify;

    /**
     * 黑名单标识
     */
    private Byte isBlacklist;

    /**
     * 是否删除标识
     */
    @TableLogic
    private Byte isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}