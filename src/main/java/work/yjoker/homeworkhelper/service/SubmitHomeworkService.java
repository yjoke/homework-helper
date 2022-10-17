package work.yjoker.homeworkhelper.service;

import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.SubmitHomeworkDTO;
import work.yjoker.homeworkhelper.entity.SubmitHomework;
import com.baomidou.mybatisplus.extension.service.IService;
import work.yjoker.homeworkhelper.vo.SubmitHomeworkVO;

import java.util.List;

/**
 *
 */
public interface SubmitHomeworkService extends IService<SubmitHomework> {

    ApiResult<String> submitHomework(SubmitHomeworkVO submitHomeworkVO);

    ApiResult<List<SubmitHomeworkDTO>> submittedList(Long homeworkId);
}
