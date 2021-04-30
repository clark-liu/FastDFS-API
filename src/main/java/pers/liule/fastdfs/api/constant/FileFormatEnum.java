package pers.liule.fastdfs.api.constant;

/**
 * @Description: 允许上传的文件的格式
 * @Author: liule
 * @Date: 2020/8/7 17:05
 */
public enum FileFormatEnum {

    /**
     * PNG
     */
    PNG(".png"),

    /**
     * JPG
     */
    JPG(".jpg"),

    /**
     * PDF
     */
    PDF(".pdf"),


    /**
     * MP4
     */
    MP4(".mp4"),

    /**
     * MOV
     */
    MOV(".mov");


    private final String type;

    FileFormatEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
