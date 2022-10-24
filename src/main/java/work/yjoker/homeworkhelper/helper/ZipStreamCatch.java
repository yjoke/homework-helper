package work.yjoker.homeworkhelper.helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author HeYunjia
 */
public class ZipStreamCatch {

    /**
     * 创建一个 zip 流缓冲
     * @throws IOException 临时文件创建失败抛出
     */
    public ZipStreamCatch() throws IOException {
        if (Files.notExists(Paths.get(CATCH_DIRECTORY)))
            Files.createDirectory(Paths.get(CATCH_DIRECTORY));

        this.zipFileName = CATCH_DIRECTORY + SLASH + UUID.randomUUID() + ZIP_SUFFIX;

        File zipFile = new File(this.zipFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);

        this.zipStream = new ZipOutputStream(fileOutputStream);
    }

    /**
     * 向 zip 流缓存中添加一个文件
     *
     * @param fileName 文件名
     * @param inputStream 文件流
     * @throws IOException 抛出 IO 异常
     */
    public void appendFile(String fileName, InputStream inputStream) throws IOException {
        if (finished) throw new IOException("流缓存已经关闭, 无法继续添加文件");

        zipStream.putNextEntry(new ZipEntry(fileName));
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            zipStream.write(buffer, ZERO, len);
        }
        zipStream.closeEntry();
    }

    /**
     * 关闭缓存区, 获取 zip 文件的输入流
     *
     * @return 返回 zip 文件的输入流
     */
    public InputStream getInputStream() {
        try {
            zipStream.close();
            this.finished = true;
            return new FileInputStream(new File(zipFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除压缩文件
     */
    public void delete() {
        try {
            if (!finished) throw new IOException("请先结束输入流");
            if (deleted) throw new IOException("文件已经删除");
            Files.delete(Paths.get(zipFileName));
            this.deleted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 压缩是否结束
     */
    private boolean finished = false;

    /**
     * 压缩文件是否已经删除
     */
    private boolean deleted = false;

    /**
     * 压缩文件的名字
     */
    private final String zipFileName;

    /**
     * 写入压缩文件的流
     */
    private final ZipOutputStream zipStream;

    /**
     * 写缓冲区
     */
    private final byte[] buffer = new byte[BUFFER_SIZE];

    /**
     * 0, 避免魔法值
     */
    private static final int ZERO = 0;

    /**
     * 写缓冲区大小
     */
    private static final int BUFFER_SIZE = 2048;

    /**
     * 斜杠, 避免魔法值
     */
    private static final String SLASH = "/";

    /**
     * zip 压缩文件后缀
     */
    private static final String ZIP_SUFFIX = ".zip";

    /**
     * 系统临时文件保存区
     */
    private static final String TMPDIR = System.getProperty("java.io.tmpdir");

    /**
     * 类读写操作的根目录
     */
    private static final String CATCH_DIRECTORY = TMPDIR + "ZipFileCatch";
}
