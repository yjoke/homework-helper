package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.UserInfoDTO;
import work.yjoker.homeworkhelper.entity.SelectCourse;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import work.yjoker.homeworkhelper.service.LoginInfoService;
import work.yjoker.homeworkhelper.service.SelectCourseService;
import work.yjoker.homeworkhelper.mapper.SelectCourseMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.RegEx;
import javax.annotation.Resource;
import java.util.List;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 *
 */
@Service
public class SelectCourseServiceImpl extends ServiceImpl<SelectCourseMapper, SelectCourse>
    implements SelectCourseService{

    @Resource
    private SelectCourseMapper selectCourseMapper;

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Override
    public ApiResult<List<UserInfoDTO>> studentList(Long courseId) {

        Long createId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));
        // TODO 这个查询应该做成分页结果,
        List<UserInfoDTO> userInfoDTOS = selectCourseMapper.selectStudentListByCourseId(courseId, createId);

        // TODO 查询在加一个成绩结果
        // TODO 结果还需要一个排序, 排序应该是选择性排序, 默认顺序和成绩排序

        return ApiResult.success(userInfoDTOS);
    }
}




