package pers.liule.fastdfs.api.common.resp.enums;


/**
 * @ClassName: DfsErrorStatus
 * @Description: fastdfs 统一错误
 * @author: liule
 * @date: 2020-08-07
 */
public enum DfsErrorStatusEnum implements ErrorStatus  {
    /**
     * DfsErrorStatus
     */
    DFS_SERVICE_OPERATION_ERROR(10001, "文件服务器未知错误"),
    DFS_FILE_IS_NOT_VALID(10002, "文件校验失败"),
    DFS_FILE_FORMAT_ILLEGAL(10003, "文件格式不合法"),
    DFS_FILE_SIZE_MORE_THEN_MIN(10004, "文件不能小于指定最小值"),
    DFS_FILE_SIZE_LESS_THEN_MIN(10005, "文件不能大于指定最大值"),
    DFS_FILE_IS_NULL(10006, "上传文件不能为空");

    private final int code;
    private final String message;

    DfsErrorStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }


    @Override
    public String getMessage() {
        return this.message;
    }


}
