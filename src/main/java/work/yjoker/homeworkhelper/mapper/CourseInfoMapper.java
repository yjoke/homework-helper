package work.yjoker.homeworkhelper.mapper;

import work.yjoker.homeworkhelper.dto.CourseInfoDTO;
import work.yjoker.homeworkhelper.entity.CourseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * work.yjoker.homeworkhelper.entity.CourseInfo
 */
public interface CourseInfoMapper extends BaseMapper<CourseInfo> {

    List<CourseInfoDTO> selectCreatedDTOByCreateId(Long createId);

    List<CourseInfoDTO> selectAddedDTOByStudentId(Long studentId);

    CourseInfoDTO selectDTOById(Long courseId);
}




