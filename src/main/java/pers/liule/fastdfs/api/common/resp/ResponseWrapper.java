package pers.liule.fastdfs.api.common.resp;


import pers.liule.fastdfs.api.common.resp.enums.ErrorStatus;

/**
 * @ClassName: ResponseWrapper
 * @Description: 统一响应结构包装类
 * @author: liule
 * @date: 2019-10-16
 */
public class ResponseWrapper {

    // 响应状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应数据
    private Object data;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }


    public ResponseWrapper() {
    }

    private ResponseWrapper(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return null != this.code && code.intValue() == 0;
    }

    private static ResponseWrapper build(Integer code, String message, Object data) {
        return new ResponseWrapper(code, message, data);
    }

    /**
     * 成功响应
     *
     * @return
     */
    public static ResponseWrapper success() {
        return build(0, "成功", null);
    }

    /**
     * 成功响应
     *
     * @param message
     * @return
     */
    public static ResponseWrapper success(String message) {
        return build(0, message, null);
    }

    /**
     * 成功响应
     *
     * @param data
     * @return
     */
    public static ResponseWrapper success(Object data) {
        return build(0, "成功", data);
    }

    /**
     * 成功响应
     *
     * @param message
     * @param data
     * @return
     */
    public static ResponseWrapper success(String message, Object data) {
        return build(0, message, data);
    }

    /**
     * 报错响应
     *
     * @param errorStatus
     * @return
     */
    public static ResponseWrapper error(ErrorStatus errorStatus) {
        return build(errorStatus.getCode(), errorStatus.getMessage(), null);
    }

    /**
     * 报错响应
     *
     * @param errorStatus
     * @param message
     * @return
     */
    public static ResponseWrapper error(ErrorStatus errorStatus, String message) {
        return build(errorStatus.getCode(), message, null);
    }

    /**
     * 报错响应
     *
     * @param errorStatus
     * @param data
     * @return
     */
    public static ResponseWrapper error(ErrorStatus errorStatus, Object data) {
        return build(errorStatus.getCode(), errorStatus.getMessage(), data);
    }

    /**
     * 报错响应
     *
     * @param errorStatus
     * @param message
     * @param data
     * @return
     */
    public static ResponseWrapper error(ErrorStatus errorStatus, String message, Object data) {
        return build(errorStatus.getCode(), message, data);
    }
}
