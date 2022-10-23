package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.SubmitHomeworkDTO;
import work.yjoker.homeworkhelper.entity.SubmitHomework;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import work.yjoker.homeworkhelper.service.AssignHomeworkService;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.service.SubmitHomeworkService;
import work.yjoker.homeworkhelper.mapper.SubmitHomeworkMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.vo.SubmitHomeworkVO;

import javax.annotation.Resource;

import java.util.List;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 *
 */
@Service
public class SubmitHomeworkServiceImpl extends ServiceImpl<SubmitHomeworkMapper, SubmitHomework>
    implements SubmitHomeworkService{

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Resource
    private SubmitHomeworkMapper submitHomeworkMapper;

    @Resource
    private AssignHomeworkService assignHomeworkService;

    @Resource
    private OssWrapper ossWrapper;

    public static final int isDeleted = 1;
    public static final int notDeleted = 0;

    @Override
    @Transactional
    public ApiResult<String> submitHomework(SubmitHomeworkVO submitHomeworkVO) {

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (!assignHomeworkService.hasPrivilege(userId, submitHomeworkVO.getAssignId())) {
            return ApiResult.fail("没有权限提交作业");
        }

        lambdaUpdate()
                .eq(SubmitHomework::getAssignId, submitHomeworkVO.getAssignId())
                .eq(SubmitHomework::getStudentId, userId)
                .eq(SubmitHomework::getIsDeleted, notDeleted)
                .set(SubmitHomework::getIsDeleted, isDeleted)
                .update();

        SubmitHomework submitHomework = submitHomeworkVO.toSubmitHomework(userId, ossWrapper.getUrlPrefix().length());

        return save(submitHomework)
                ? ApiResult.success("提交成功")
                : ApiResult.fail("提交失败");
    }

    @Override
    public ApiResult<List<SubmitHomeworkDTO>> submittedList(Long homeworkId) {

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (!assignHomeworkService.isTeacher(userId, homeworkId)) {
            return ApiResult.fail("没有权限查看该作业提交列表");
        }

        List<SubmitHomeworkDTO> dtoList = submitHomeworkMapper.selectDTOById(homeworkId);

        return null;
    }


}




