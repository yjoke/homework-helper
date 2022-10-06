package work.yjoker.homeworkhelper.mapper;

import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.entity.SelectCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity work.yjoker.homeworkhelper.entity.SelectCourse
 */
public interface SelectCourseMapper extends BaseMapper<SelectCourse> {

    List<UserInfoDTO> selectStudentListByCourseId(Long courseId, Long createId);

}




