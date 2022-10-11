package work.yjoker.homeworkhelper.dto;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.entity.CourseResource;

import java.util.Date;

/**
 * @author HeYunjia
 */
@Data
@ApiModel("课程资源信息, 返回数据")
public class CourseResourceDTO {

    @ApiModelProperty("课程的 id")
    private Long courseId;

    @ApiModelProperty("资源的名字")
    private String resourceName;

    @ApiModelProperty("资源的地址")
    private String resourceUrl;

    @ApiModelProperty("资源的大小")
    private Integer resourceSize;

    @ApiModelProperty("上传时间")
    private Date gmtCreate;

    /**
     * Entity to DTO
     */
    public static CourseResourceDTO toCourseResourceDTO(CourseResource courseResource, String urlPrefix) {

        CourseResourceDTO courseResourceDTO = BeanUtil.copyProperties(courseResource, CourseResourceDTO.class);

        courseResourceDTO.setResourceUrl(urlPrefix + courseResource.getResourceUrl());

        return courseResourceDTO;
    }
}
