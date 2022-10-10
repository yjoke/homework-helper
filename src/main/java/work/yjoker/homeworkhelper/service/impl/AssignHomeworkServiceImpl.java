package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.AssignHomeworkDTO;
import work.yjoker.homeworkhelper.entity.AssignHomework;
import work.yjoker.homeworkhelper.service.AssignHomeworkService;
import work.yjoker.homeworkhelper.mapper.AssignHomeworkMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.service.CourseInfoService;
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

    @Override
    @Transactional
    public ApiResult<String> assignHomework(AssignHomeworkVO assignHomeworkVO) {

        if (!courseInfoService.hasPrivilege(Holder.get(PHONE_HOLDER), assignHomeworkVO.getCourseId())) {
            return ApiResult.fail("没有权限添加作业");
        }

        return save(assignHomeworkVO.toAssignHomework())
                ? ApiResult.fail("上传失败")
                : ApiResult.success("发布成功");
    }

    @Override
    public ApiResult<String> removeHomework(Long homeworkId) {

        AssignHomework info = lambdaQuery()
                .eq(AssignHomework::getId, homeworkId)
                .one();

        if (info == null ||
                !courseInfoService.hasPrivilege(Holder.get(PHONE_HOLDER), info.getCourseId())) {
            return ApiResult.fail("没有权限添加作业");
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

        if (info == null
                || !info.getCourseId().equals(assignHomeworkVO.getCourseId())
                || !courseInfoService.hasPrivilege(Holder.get(PHONE_HOLDER), info.getCourseId())) {
            return ApiResult.fail("没有权限修改作业");
        }

        AssignHomework assignHomework = assignHomeworkVO.toAssignHomework();
        assignHomework.setId(homeworkId);

        return updateById(assignHomework)
                ? ApiResult.success("修改成功")
                : ApiResult.fail("修改失败");
    }

    @Override
    public ApiResult<List<AssignHomeworkDTO>> assignList(Long courseId) {

        if (!courseInfoService.hasPrivilege(Holder.get(PHONE_HOLDER), courseId)) {
            return ApiResult.fail("没有权限获取数据");
        }

        List<AssignHomework> list = lambdaQuery()
                .eq(AssignHomework::getCourseId, courseId)
                .list();

        List<AssignHomeworkDTO> dtoList = list.stream()
                .map(AssignHomeworkDTO::toAssignHomeworkDTO)
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

        if (info == null ||
                !courseInfoService.hasPrivilege(Holder.get(PHONE_HOLDER), info.getCourseId())) {
            return ApiResult.fail("没有权限进行操作");
        }

        info.setGmtExpire(expireTime);

        return updateById(info)
                ? ApiResult.success("操作成功")
                : ApiResult.fail("操作失败");
    }
}




