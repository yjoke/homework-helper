package work.yjoker.homeworkhelper.mapper;

import work.yjoker.homeworkhelper.dto.SubmitHomeworkDTO;
import work.yjoker.homeworkhelper.entity.SubmitHomework;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * work.yjoker.homeworkhelper.entity.SubmitHomework
 */
public interface SubmitHomeworkMapper extends BaseMapper<SubmitHomework> {

    List<SubmitHomeworkDTO> selectDTOById(Long homeworkId);
}




