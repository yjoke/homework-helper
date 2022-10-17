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

    ApiResult<CourseInfoDTO> courseInfoDTO(Long id);

    ApiResult<String> modifyCourseInfo(CourseInfoDTO courseInfoDTO);

    ApiResult<String> code(Long id);

    ApiResult<String> modifyCode(Long id);

    ApiResult<CourseInfoDTO> addedCourse(String code);

    ApiResult<List<CourseInfoDTO>> added();

    boolean hasPrivilege(Long userId, Long courseId);

    boolean isTeacher(Long userId, Long courseId);

    boolean isStudent(Long userId, Long courseId);
}
