package work.yjoker.homeworkhelper.service;

import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.CourseResourceDTO;
import work.yjoker.homeworkhelper.entity.CourseResource;
import com.baomidou.mybatisplus.extension.service.IService;
import work.yjoker.homeworkhelper.vo.CourseResourceVO;

import java.util.List;

/**
 *
 */
public interface CourseResourceService extends IService<CourseResource> {

    ApiResult<CourseResourceDTO> saveCourseResource(CourseResourceVO courseResourceVO);

    ApiResult<List<CourseResourceDTO>> dtoList(Long courseId);
}
