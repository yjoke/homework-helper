package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.CourseResourceDTO;
import work.yjoker.homeworkhelper.entity.CourseInfo;
import work.yjoker.homeworkhelper.entity.CourseResource;
import work.yjoker.homeworkhelper.entity.SelectCourse;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.service.CourseResourceService;
import work.yjoker.homeworkhelper.mapper.CourseResourceMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.service.SelectCourseService;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.vo.CourseResourceVO;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 *
 */
@Service
public class CourseResourceServiceImpl extends ServiceImpl<CourseResourceMapper, CourseResource>
    implements CourseResourceService{

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Resource
    private CourseInfoService courseInfoService;

    @Resource
    private SelectCourseService selectCourseService;

    @Resource
    private OssWrapper ossWrapper;

    @Override
    public ApiResult<List<CourseResourceDTO>> dtoList(Long courseId) {

        if (!hasPrivilege(Holder.get(PHONE_HOLDER), courseId)) {
            return ApiResult.fail("没有访问权限");
        }

        List<CourseResource> list = lambdaQuery()
                .eq(CourseResource::getCourseId, courseId)
                .list();

        List<CourseResourceDTO> dtoList = list.stream()
                .map(entity ->
                        CourseResourceDTO.toCourseResourceDTO(entity, ossWrapper.getUrlPrefix().length()))
                .collect(Collectors.toList());

        return ApiResult.success(dtoList);
    }

    @Override
    public ApiResult<String> saveCourseResource(CourseResourceVO courseResourceVO) {

        String phone = Holder.get(PHONE_HOLDER);

        Long userId = loginInfoMapper.selectIdByPhone(phone);

        if (!isTeacher(userId, courseResourceVO.getCourseId())) {
            return ApiResult.fail("没有权限上传文件到该课程");
        }

        CourseResource courseResource = courseResourceVO.toCourseResource(ossWrapper.getUrlPrefix());

        return save(courseResource)
                ? ApiResult.success("上传成功")
                : ApiResult.fail("上传失败");
    }


    /**
     * 判断是否有权限访问资源列表
     *
     * @param phone 访问人手机号
     * @param courseId 要获取的课程 id
     * @return 有权限返回 true
     */
    private boolean hasPrivilege(String phone, Long courseId) {

        Long userId = loginInfoMapper.selectIdByPhone(phone);

        return isTeacher(userId, courseId) || isStudent(userId, courseId);
    }

    /**
     * 判断是否是课程老师
     *
     * @param userId 访问用户
     * @param courseId 课程 id
     * @return 是 返回 true
     */
    private boolean isTeacher(Long userId, Long courseId) {

        CourseInfo courseInfo = courseInfoService.lambdaQuery()
                .eq(CourseInfo::getCreateId, userId)
                .eq(CourseInfo::getId, courseId)
                .one();

        return courseInfo != null;
    }

    /**
     * 判断是否是课程学生
     *
     * @param userId 访问用户
     * @param courseId 课程 id
     * @return 是 返回 true
     */
    private boolean isStudent(Long userId, Long courseId) {

        SelectCourse selectCourse = selectCourseService.lambdaQuery()
                .eq(SelectCourse::getStudentId, userId)
                .eq(SelectCourse::getCourseId, courseId)
                .one();

        return selectCourse != null;
    }
}




