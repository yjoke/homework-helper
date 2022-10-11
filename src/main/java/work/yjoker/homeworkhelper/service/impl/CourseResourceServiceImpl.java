package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.CourseResourceDTO;
import work.yjoker.homeworkhelper.entity.CourseResource;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import work.yjoker.homeworkhelper.service.CourseInfoService;
import work.yjoker.homeworkhelper.service.CourseResourceService;
import work.yjoker.homeworkhelper.mapper.CourseResourceMapper;
import org.springframework.stereotype.Service;
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
    private OssWrapper ossWrapper;

    @Override
    public ApiResult<List<CourseResourceDTO>> dtoList(Long courseId) {

        if (!courseInfoService.hasPrivilege(Holder.get(PHONE_HOLDER), courseId)) {
            return ApiResult.fail("没有访问权限");
        }

        List<CourseResource> list = lambdaQuery()
                .eq(CourseResource::getCourseId, courseId)
                .list();

        List<CourseResourceDTO> dtoList = list.stream()
                .map(entity ->
                        CourseResourceDTO.toCourseResourceDTO(entity, ossWrapper.getUrlPrefix()))
                .collect(Collectors.toList());

        return ApiResult.success(dtoList);
    }

    @Override
    public ApiResult<CourseResourceDTO> saveCourseResource(CourseResourceVO courseResourceVO) {

        String phone = Holder.get(PHONE_HOLDER);

        Long userId = loginInfoMapper.selectIdByPhone(phone);

        if (!courseInfoService.isTeacher(userId, courseResourceVO.getCourseId())) {
            return ApiResult.fail("没有权限上传文件到该课程");
        }

        CourseResource courseResource = courseResourceVO.toCourseResource(ossWrapper.getUrlPrefix().length());

        if (!save(courseResource)) return ApiResult.fail("上传失败");

        CourseResourceDTO courseResourceDTO =
                CourseResourceDTO.toCourseResourceDTO(courseResource, ossWrapper.getUrlPrefix());

        return ApiResult.success(courseResourceDTO);
    }

}




