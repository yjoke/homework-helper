package work.yjoker.homeworkhelper.service;

import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.AssignHomeworkDTO;
import work.yjoker.homeworkhelper.entity.AssignHomework;
import com.baomidou.mybatisplus.extension.service.IService;
import work.yjoker.homeworkhelper.vo.AssignHomeworkVO;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface AssignHomeworkService extends IService<AssignHomework> {

    ApiResult<String> assignHomework(AssignHomeworkVO assignHomeworkVO);

    ApiResult<String> removeHomework(Long homeworkId);

    ApiResult<String> modifyHomework(Long homeworkId, AssignHomeworkVO assignHomeworkVO);

    ApiResult<List<AssignHomeworkDTO>> assignList(Long courseId);

    ApiResult<String> closeSubmit(Long homeworkId);

    ApiResult<String> openHomework(Long homeworkId, Date expireTime);
}
