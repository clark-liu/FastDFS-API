package pers.liule.fastdfs.api.common.resp.enums;

/**
 * @ClassName: ErrorStatus
 * @Description: 统一响应错误状态码枚举
 * @author: liule
 * @date: 2019-10-16
 */
public interface ErrorStatus {
    /**
     * 获取状态码
     *
     * @return
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getMessage();
}
