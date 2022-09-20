package work.yjoker.homeworkhelper.service;

import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.CourseInfoDTO;
import work.yjoker.homeworkhelper.entity.CourseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface CourseInfoService extends IService<CourseInfo> {

    ApiResult<String> saveCourse(CourseInfoDTO courseInfoDTO);

    ApiResult<List<CourseInfoDTO>> created();
}
