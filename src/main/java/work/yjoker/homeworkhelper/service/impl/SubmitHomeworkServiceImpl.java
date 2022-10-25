package work.yjoker.homeworkhelper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.dto.SubmitHomeworkDTO;
import work.yjoker.homeworkhelper.entity.SubmitHomework;
import work.yjoker.homeworkhelper.helper.ZipStreamCatch;
import work.yjoker.homeworkhelper.mapper.LoginInfoMapper;
import work.yjoker.homeworkhelper.service.AssignHomeworkService;
import work.yjoker.homeworkhelper.service.SubmitHomeworkService;
import work.yjoker.homeworkhelper.mapper.SubmitHomeworkMapper;
import org.springframework.stereotype.Service;
import work.yjoker.homeworkhelper.util.Holder;
import work.yjoker.homeworkhelper.util.MemoryUnit;
import work.yjoker.homeworkhelper.vo.SubmitHomeworkVO;

import javax.annotation.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
            return ApiResult.fail("没有权限");
        }

        List<SubmitHomeworkDTO> dtoList = submitHomeworkMapper.selectSubmitDTOListByHomeworkId(homeworkId);

        dtoList.forEach(info -> info.refactorURL(ossWrapper.getUrlPrefix()));
        dtoList.forEach(SubmitHomeworkDTO::refactorInfo);

        return ApiResult.success(dtoList);
    }

    @Override
    public ApiResult<List<SubmitHomeworkDTO>> notSubmittedList(Long homeworkId) {

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (!assignHomeworkService.isTeacher(userId, homeworkId)) {
            return ApiResult.fail("没有权限");
        }

        List<SubmitHomeworkDTO> dtoList = submitHomeworkMapper.selectNotSubmitDTOListByHomeworkId(homeworkId);

        return ApiResult.success(dtoList);
    }

    @Override
    public ApiResult<String> zipFile(Long homeworkId) {

        Long userId = loginInfoMapper.selectIdByPhone(Holder.get(PHONE_HOLDER));

        if (!assignHomeworkService.isTeacher(userId, homeworkId)) {
            return ApiResult.fail("没有权限");
        }

        List<SubmitHomeworkDTO> submitList = submitHomeworkMapper.selectSubmitDTOListByHomeworkId(homeworkId);
        submitList.forEach(SubmitHomeworkDTO::refactorName);
        List<SubmitHomeworkDTO> notSubmitList = submitHomeworkMapper.selectNotSubmitDTOListByHomeworkId(homeworkId);
        notSubmitList.forEach(SubmitHomeworkDTO::refactorInfo);

        try {
            ZipStreamCatch zipStreamCatch = new ZipStreamCatch();

            // 添加提交的作业数据
            for (SubmitHomeworkDTO dto : submitList) {
                InputStream inputStream = ossWrapper.downFile(dto.getResourceUrl());
                zipStreamCatch.appendFile(dto.getResourceName(), inputStream);
                inputStream.close();
            }

            // 添加未提交的名单
            StringBuilder sb = new StringBuilder();
            for (SubmitHomeworkDTO dto : notSubmitList) {
                sb.append(dto.getStudentInfo()).append("\n");
            }

            ByteArrayInputStream notSubmitFile = new ByteArrayInputStream(sb.toString().getBytes());
            zipStreamCatch.appendFile("未提交作业的学生名单.txt", notSubmitFile);
            notSubmitFile.close();

            InputStream zipInput = zipStreamCatch.getInputStream();
            String zipUrl = ossWrapper.saveFile(zipInput, zipInput.available(), MemoryUnit.BYTE, ".zip");

            return ApiResult.success("zipUrl", ossWrapper.getUrlPrefix() + zipUrl);
        } catch (IOException e) {
            return ApiResult.error("服务器发生异常, 请稍后重试");
        }
    }

}




