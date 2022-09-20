package work.yjoker.homeworkhelper.service;

import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.entity.CourseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface CourseInfoService extends IService<CourseInfo> {

    ApiResult<String> saveCourse(CourseInfo courseInfo);
}
