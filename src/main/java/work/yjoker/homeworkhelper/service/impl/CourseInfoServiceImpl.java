package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import work.yjoker.homeworkhelper.common.wrapper.InvitationCodeWrapper;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.CourseInfoDTO;
import work.yjoker.homeworkhelper.entity.CourseInfo;
import work.yjoker.homeworkhelper.entity.SelectCourse;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.mapper.CourseInfoMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.service.SelectCourseService;
import work.yjoker.homeworkhelper.util.Holder;

import javax.annotation.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static work.yjoker.homeworkhelper.constant.ApiResultConstants.NOT_LOGIN;
import static work.yjoker.homeworkhelper.util.Holder.PHONE_HOLDER;

/**
 *
 */
@Slf4j
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo>
    implements CourseInfoService{

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Resource
    private CourseInfoMapper courseInfoMapper;

    @Resource
    private SelectCourseService selectCourseService;

    @Resource
    private OssWrapper ossWrapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private InvitationCodeWrapper invitationCodeWrapper;

    @Override
    public ApiResult<String> saveCourse(CourseInfoDTO courseInfoDTO) {

        Long createId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        CourseInfo courseInfo = courseInfoDTO.toCourseInfo(createId, ossWrapper.getUrlPrefix().length());

        boolean save = save(courseInfo);
        log.info("courseId {}", courseInfo.getId());
        return save
                ? ApiResult.success("id", courseInfo.getId().toString())
                : ApiResult.fail("????????????");
    }

    @Override
    public ApiResult<List<CourseInfoDTO>> created() {

        Long createId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        List<CourseInfoDTO> courseInfoDTOS = courseInfoMapper.selectCreatedDTOByCreateId(createId);

        courseInfoDTOS.forEach(info -> {
            String courseImg = info.getCourseImg();
            courseImg = ossWrapper.getUrlPrefix() + courseImg;
            info.setCourseImg(courseImg);
        });

        return ApiResult.success(courseInfoDTOS);
    }

    @Override  // TODO, ????????????, ???????????????
    public ApiResult<CourseInfoDTO> courseInfoDTO(Long id) {
        String phone = Holder.get(PHONE_HOLDER);
        Long userId = loginInfoMapper.selectIdByPhone(phone);
        if (userId == null) return ApiResult.fail(NOT_LOGIN);

        if (!hasPrivilege(userId, id)) return ApiResult.fail("???????????????????????????");

        CourseInfo courseInfo = lambdaQuery()
                .eq(CourseInfo::getId, id)
                .one();
        if (courseInfo == null) return ApiResult.fail("???????????????");

        CourseInfoDTO courseInfoDTO = CourseInfoDTO.toCourseInfoDTO(courseInfo, ossWrapper.getUrlPrefix());

        return ApiResult.success(courseInfoDTO);
    }

    @Override
    public ApiResult<String> modifyCourseInfo(CourseInfoDTO courseInfoDTO) {
        String phone = Holder.get(PHONE_HOLDER);

        Long createdId = loginInfoMapper.selectIdByPhone(phone);

        CourseInfo courseInfo = courseInfoDTO.toCourseInfo(createdId, ossWrapper.getUrlPrefix().length());

        boolean update = lambdaUpdate()
                .eq(CourseInfo::getId, courseInfo.getId())
                .eq(CourseInfo::getCreateId, createdId)
                .update(courseInfo);

        return update
                ? ApiResult.success("????????????")
                : ApiResult.fail("????????????");
    }

    @Override
    public ApiResult<String> code(Long id) {

        String code = stringRedisTemplate.opsForValue()
                .get(String.valueOf(id));
        if (code == null) code = "";

        return ApiResult.success("code", code);
    }

    @Override
    // TODO ???????????????????????????????????????, ????????????,
    public ApiResult<String> modifyCode(Long id) {

        String strId = String.valueOf(id);

        String code = stringRedisTemplate.opsForValue().get(strId);
        if (code != null) stringRedisTemplate.delete(code);

        stringRedisTemplate.delete(strId);

        String newCode = invitationCodeWrapper.getInvitationCode();

        stringRedisTemplate.opsForValue().set(strId, newCode, 7, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(newCode, strId, 7, TimeUnit.DAYS);

        return ApiResult.success("code", newCode);
    }

    @Override
    public ApiResult<List<CourseInfoDTO>> added() {

        Long studentId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        List<CourseInfoDTO> courseInfoDTOS = courseInfoMapper.selectAddedDTOByStudentId(studentId);

        courseInfoDTOS.forEach(info -> {
            String courseImg = info.getCourseImg();
            courseImg = ossWrapper.getUrlPrefix() + courseImg;
            info.setCourseImg(courseImg);
        });

        return ApiResult.success(courseInfoDTOS);
    }

    @Override
    public ApiResult<CourseInfoDTO> addedCourse(String code) {

        // ???????????????????????????
        String courseId = stringRedisTemplate.opsForValue().get(code);
        if (StrUtil.isEmpty(courseId)) return ApiResult.fail("???????????????");

        // ????????????????????????
        Long studentId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        SelectCourse one = selectCourseService.lambdaQuery()
                .eq(SelectCourse::getCourseId, courseId)
                .eq(SelectCourse::getStudentId, studentId)
                .one();

        if (ObjectUtil.isNotNull(one)) return ApiResult.fail("??????????????????");

        // ??????????????????????????????
        CourseInfo courseInfo = lambdaQuery()
                .eq(CourseInfo::getId, courseId)
                .one();

        if (courseInfo.getCreateId().equals(studentId)) return ApiResult.fail("??????????????????????????????");

        // ???????????????
        SelectCourse selectCourse = new SelectCourse();

        selectCourse.setCourseId(Long.valueOf(courseId));
        selectCourse.setStudentId(studentId);

        if (!selectCourseService.save(selectCourse)) return ApiResult.fail("????????????");

        // ??????????????????????????????
        CourseInfoDTO courseInfoDTO = courseInfoMapper.selectDTOById(courseInfo.getId());

        String courseImg = courseInfoDTO.getCourseImg();
        courseImg = ossWrapper.getUrlPrefix() + courseImg;
        courseInfoDTO.setCourseImg(courseImg);

        return ApiResult.success(courseInfoDTO);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param userId ????????? id
     * @param courseId ?????????????????? id
     * @return ??????????????? true
     */
    public boolean hasPrivilege(Long userId, Long courseId) {
        return isTeacher(userId, courseId) || isStudent(userId, courseId);
    }

    /**
     * ???????????????????????????
     *
     * @param userId ????????????
     * @param courseId ?????? id
     * @return ??? ?????? true
     */
    public boolean isTeacher(Long userId, Long courseId) {

        CourseInfo courseInfo = lambdaQuery()
                .eq(CourseInfo::getId, courseId)
                .eq(CourseInfo::getCreateId, userId)
                .one();

        return courseInfo != null;
    }

    /**
     * ???????????????????????????
     *
     * @param userId ????????????
     * @param courseId ?????? id
     * @return ??? ?????? true
     */
    public boolean isStudent(Long userId, Long courseId) {

        SelectCourse selectCourse = selectCourseService.lambdaQuery()
                .eq(SelectCourse::getStudentId, userId)
                .eq(SelectCourse::getCourseId, courseId)
                .one();

        return selectCourse != null;
    }
}




