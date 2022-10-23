package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.AssignHomeworkDTO;
import work.yjoker.homeworkhelper.entity.AssignHomework;
import work.yjoker.homeworkhelper.entity.SubmitHomework;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import work.yjoker.homeworkhelper.service.AssignHomeworkService;
import work.yjoker.homeworkhelper.mapper.AssignHomeworkMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.service.SubmitHomeworkService;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.vo.AssignHomeworkVO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 *
 */
@Service
public class AssignHomeworkServiceImpl extends ServiceImpl<AssignHomeworkMapper, AssignHomework>
    implements AssignHomeworkService{

    @Resource
    private CourseInfoService courseInfoService;

    @Resource
    private SubmitHomeworkService submitHomeworkService;

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Resource
    private OssWrapper ossWrapper;

    @Override
    @Transactional
    public ApiResult<AssignHomeworkDTO> assignHomework(AssignHomeworkVO assignHomeworkVO) {

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (!courseInfoService.isTeacher(userId, assignHomeworkVO.getCourseId())) {
            return ApiResult.fail("没有权限添加作业");
        }

        AssignHomework entity = assignHomeworkVO.toAssignHomework();
        if (!save(entity)) ApiResult.fail("添加失败");

        entity = lambdaQuery()
                .eq(AssignHomework::getId, entity.getId())
                .one();
        AssignHomeworkDTO assignHomeworkDTO = AssignHomeworkDTO.toAssignHomeworkDTO(entity);

        return ApiResult.success(assignHomeworkDTO);
    }

    @Override
    public ApiResult<String> removeHomework(Long homeworkId) {

        AssignHomework info = lambdaQuery()
                .eq(AssignHomework::getId, homeworkId)
                .one();

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (info == null || !courseInfoService.isTeacher(userId, info.getCourseId())) {
            return ApiResult.fail("没有权限删除作业");
        }

        return removeById(homeworkId)
                ? ApiResult.success("删除成功")
                : ApiResult.fail("删除失败");
    }

    @Override
    public ApiResult<String> modifyHomework(Long homeworkId, AssignHomeworkVO assignHomeworkVO) {

        AssignHomework info = lambdaQuery()
                .eq(AssignHomework::getId, homeworkId)
                .one();

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (info == null
                || !info.getCourseId().equals(assignHomeworkVO.getCourseId())
                || !courseInfoService.isTeacher(userId, info.getCourseId())) {
            return ApiResult.fail("没有权限修改该作业");
        }

        AssignHomework assignHomework = assignHomeworkVO.toAssignHomework();
        assignHomework.setId(homeworkId);

        return updateById(assignHomework)
                ? ApiResult.success("修改成功")
                : ApiResult.fail("修改失败");
    }

    @Override
    public ApiResult<List<AssignHomeworkDTO>> assignList(Long courseId) {

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (!courseInfoService.hasPrivilege(userId, courseId)) {
            return ApiResult.fail("没有权限获取数据");
        }

        List<AssignHomework> list = lambdaQuery()
                .eq(AssignHomework::getCourseId, courseId)
                .list();

        // TODO 这里应该可以进行一个重构, 看着嵌套有点深了
        List<AssignHomeworkDTO> dtoList = list.stream()
                .map((entity) -> {
                    AssignHomeworkDTO assignHomeworkDTO = AssignHomeworkDTO.toAssignHomeworkDTO(entity);

                    // TODO 这里没有处理 url 的前缀
                    SubmitHomework submitInfo = submitHomeworkService.lambdaQuery()
                            .eq(SubmitHomework::getAssignId, entity.getId())
                            .eq(SubmitHomework::getStudentId, userId)
                            .one();

                    return assignHomeworkDTO.setHomework(submitInfo, ossWrapper.getUrlPrefix());
                })
                .collect(Collectors.toList());

        return ApiResult.success(dtoList);
    }

    @Override
    public ApiResult<String> closeSubmit(Long homeworkId) {
        return openHomework(homeworkId, new Date());
    }

    @Override
    public ApiResult<String> openHomework(Long homeworkId, Date expireTime) {

        AssignHomework info = lambdaQuery()
                .eq(AssignHomework::getId, homeworkId)
                .one();

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (info == null ||
                !courseInfoService.isTeacher(userId, info.getCourseId())) {
            return ApiResult.fail("没有权限进行操作");
        }

        info.setGmtExpire(expireTime);

        return updateById(info)
                ? ApiResult.success("操作成功")
                : ApiResult.fail("操作失败");
    }

    /**
     * 判断用户是否是该作业的老师
     *
     * @param userId 用户 id
     * @param homeworkId 作业 id
     */
    public boolean isTeacher(Long userId, Long homeworkId) {
        AssignHomework info = lambdaQuery()
                .eq(AssignHomework::getId, homeworkId)
                .one();

        return info != null && courseInfoService.isTeacher(userId, info.getCourseId());
    }

    /**
     * 判断用户是否有权限提交该作业
     *
     * @param userId 用户 id
     * @param homeworkId 作业 id
     * @return 是返回 true
     */
    public boolean hasPrivilege(Long userId, Long homeworkId) {
        AssignHomework info = lambdaQuery()
                .eq(AssignHomework::getId, homeworkId)
                .one();

        return info != null
                && info.getGmtExpire().getTime() > System.currentTimeMillis()
                && courseInfoService.isStudent(userId, info.getCourseId());
    }
}




