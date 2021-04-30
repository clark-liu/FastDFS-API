package pers.liule.fastdfs.api.constant;

/**
 * @Description: 文件类型枚举类
 * @Author: liule
 * @Date: 2020/8/4 16:58
 */
public enum MultiMediaTypeEnum {

    /**
     * 视频
     */
    VIDEO(1,"video"),


    /**
     * 图片
     */
    IMAGE(2, "image"),


    /**
     * 文本
     */
    DOCUMENT(3,"doc");



    private final int typeCode;
    private final String typeName;

    MultiMediaTypeEnum(int typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public int getTypeCode() {
        return this.typeCode;
    }

    public String getTypeName() {
        return this.typeName;
    }

}
