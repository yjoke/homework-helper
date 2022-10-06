package work.yjoker.homeworkhelper.constant;

/**
 * @author HeYunjia
 */
public class FileConstants {

    /**
     * 头像大小的最大值: 4 MB
     */
    public static final long MAX_AVATAR_SIZE = 4 * 1024 * 1024;

    /**
     * 返回数据头像的 url 的 key
     */
    public static final String AVATAR_PATH_KEY = "avatar";

    /**
     * 封面大小的最大值: 5 MB
     */
    public static final long MAX_COVER_SIZE = 5 * 1024 * 1024;

    /**
     * 返回课程封面的 url 的 key
     */
    public static final String COVER_PATH_KEY = "courseImg";

    /**
     * 课程资源的最大值: 1 GB
     */
    public static final long MAX_RESOURCE_SIZE = 1024 * 1024 * 1024;

    /**
     * 返回课程资源的 url 的 key
     */
    public static final String RESOURCE_PATH_KEY = "resource";

    /**
     * 作业文件的最大值 50 MB
     */
    public static final long MAX_HOMEWORK_SIZE = 50 * 1014 * 1024;

    /**
     * 返回作业数据的 url 的 key
     */
    public static final String HOMEWORK_PATH_KEY = "homework";
}
