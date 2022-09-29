package work.yjoker.homeworkhelper.common.wrapper;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import work.yjoker.homeworkhelper.util.MemoryUnit;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author HeYunjia
 */
public class OssWrapper {

    /**
     * 必要字段
     *
     * @param endpoint bucket 地址
     * @param accessKeyId 密钥对
     * @param accessKeySecret 密钥对
     * @param bucketName bucket 名字
     * @param basePath 文件夹
     */
    public OssWrapper(String endpoint, String accessKeyId, String accessKeySecret,
               String bucketName, String basePath) {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setSupportCname(true);
        this.oss = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret, conf);

        this.urlPrefix = HTTP + endpoint + "/" + basePath;
        this.bucketName = bucketName;
        this.basePath = basePath;
    }

    /**
     * 将大小为 size 的文件流保存到 oss 中, 文件后缀为 suffix
     *
     * @param inputStream 输入流
     * @param size 文件大小
     * @param unit 大小单位
     * @param suffix 文件后缀
     * @return 返回文件保存路径
     */
    public String saveFile(InputStream inputStream, long size, MemoryUnit unit, String suffix) {

        if (inputStream == null || suffix == null || suffix.equals("")) return null;

        String filePath = getFolder(size, unit) + UUID.randomUUID() + suffix;

        try {
            oss.putObject(bucketName, basePath + filePath, inputStream);
            return filePath;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取文件地址前缀
     */
    public String getUrlPrefix() {
        return urlPrefix;
    }

    /**
     * 根据文件大小获取存储文件夹
     */
    private String getFolder(long size, MemoryUnit unit) {
        long megaByte = unit.toMegaByte(size);

        int i, base = 2;
        for (i = 1; i < 12; i++) {
            if (megaByte < base) return folders[i];
            else base *= 2;
        }

        return folders[i];
    }

    private static final String[] folders = {"",
            "max-2MB/", "max-4MB/", "max-8MB/", "max-16M/",
            "max-32MB/", "max-64MB/", "max-128MB/", "max-256MB/",
            "max-512MB/", "max-1GB/", "max-2GB/", "max-other/"
    };

    /**
     * url 头部
     */
    private static final String HTTP = "http://";

    /**
     * 客户端实例
     */
    private final OSS oss;

    /**
     * 文件的网路地址前缀
     */
    private final String urlPrefix;

    /**
     * bucket 的名字
     */
    private final String bucketName;

    /**
     * 要保存的基础目录
     */
    private final String basePath;
}
