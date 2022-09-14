package work.yjoker.homeworkhelper.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import work.yjoker.homeworkhelper.common.wrapper.OssWrapper;
import work.yjoker.homeworkhelper.dto.ApiResult;
import work.yjoker.homeworkhelper.service.FileService;
import work.yjoker.homeworkhelper.util.MemoryUnit;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author HeYunjia
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private OssWrapper ossWrapper;

    /**
     * 头像的最大
     */
    private static final long MAX_AVATAR_SIZE = 10 * 1024 * 1024;  // MB

    /**
     * 返回数据的头像 url 的 key
     */
    private static final String AVATAR_PATH_KEY = "avatar";

    @Override
    public ApiResult<String> saveAvatar(MultipartFile file) {

        ApiResult<String> fail = ApiResult.fail("上传失败");

        long size = file.getSize();
        if (size > MAX_AVATAR_SIZE) return fail;

        String filename = file.getOriginalFilename();
        if (StrUtil.isEmpty(filename)) return fail;

        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StrUtil.isEmpty(suffix)) return fail;

        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            return fail;
        }

        if (ObjectUtil.isNull(inputStream)) return fail;

        String filePath = ossWrapper.saveFile(
                inputStream,
                file.getSize(),
                MemoryUnit.BYTE,
                suffix
        );

        String avatarUrl = ossWrapper.getUrlPrefix() + filePath;

        return ApiResult.success(AVATAR_PATH_KEY, avatarUrl);
    }
}
