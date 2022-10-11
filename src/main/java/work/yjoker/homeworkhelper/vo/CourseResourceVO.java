package work.yjoker.homeworkhelper.vo;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.entity.CourseResource;

/**
 * @author HeYunjia
 */
@Data
@ApiModel("课程资源信息, 接受数据")
public class CourseResourceVO {

    @ApiModelProperty("课程的 id")
    private Long courseId;

    @ApiModelProperty("资源的名字")
    private String resourceName;

    @ApiModelProperty("资源的地址")
    private String resourceUrl;

    @ApiModelProperty("资源的大小")
    private Integer resourceSize;

    /**
     * VO to Entity
     *
     * @param urlPrefixLength url 前缀长度
     */
    public CourseResource toCourseResource(int urlPrefixLength) {

        CourseResource courseResource = BeanUtil.copyProperties(this, CourseResource.class);

        courseResource.setResourceUrl(resourceUrl.substring(urlPrefixLength));

        return courseResource;
    }
}
