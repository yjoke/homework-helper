package work.yjoker.homeworkhelper.dto;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.yjoker.homeworkhelper.entity.CourseInfo;

/**
 * @author HeYunjia
 */
@Data
@ApiModel("课程信息")
public class CourseInfoDTO {

    @ApiModelProperty("课程标识")
    private Long id;

    @ApiModelProperty("创建课程的用户")
    private String createUserName;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("课程封面")
    private String courseImg;

    /**
     * 将 DTO 转换为 entity
     * @param createId 创建人 id
     * @param urlPrefixLength courseImg 的公共前缀长度
     * @return 返回 courseInfo
     */
    public CourseInfo toCourseInfo(Long createId, int urlPrefixLength) {
        CourseInfo courseInfo = new CourseInfo();

        courseInfo.setCreateId(createId);
        courseInfo.setCourseName(this.getCourseName());
        courseInfo.setCourseImg(this.getCourseImg().substring(urlPrefixLength));

        return courseInfo;
    }

    /**
     * 将 entity 转换为 DTO
     *
     * @param courseInfo info 数据
     * @param urlPrefix courseImg 的公共前缀
     * @return 返回 courseInfoDTO
     */
    public static CourseInfoDTO toCourseInfoDTO(CourseInfo courseInfo, String urlPrefix) {
        CourseInfoDTO courseInfoDTO = BeanUtil.copyProperties(courseInfo, CourseInfoDTO.class);

        String courseImg = courseInfoDTO.getCourseImg();
        courseImg = urlPrefix + courseImg;
        courseInfoDTO.setCourseImg(courseImg);

        return courseInfoDTO;
    }
}
