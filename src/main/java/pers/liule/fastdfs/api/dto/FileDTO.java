package pers.liule.fastdfs.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pers.liule.fastdfs.api.constant.MultiMediaTypeEnum;

import java.io.Serializable;

/**
 * @Description: FileDTO
 * @Author: liule
 * @Date: 2020/8/4 16:28
 */
public class FileDTO implements Serializable {

    private static final long serialVersionUID = -4484347281275062651L;

    private String id;

    private String name;

    @JsonIgnore
    private Long size;

    private String filePath;

    private String trackerPath;

    private String format;

    private String status;

    private MultiMediaTypeEnum typeEnum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTrackerPath() {
        return trackerPath;
    }

    public void setTrackerPath(String trackerPath) {
        this.trackerPath = trackerPath;
    }

    public void setTypeEnum(MultiMediaTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MultiMediaTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public FileDTO(String name, Long size, String trackerPath, String filePath, String format) {
        this.name = name;
        this.size = size;
        this.trackerPath = trackerPath;
        this.filePath = filePath;
        this.format = format;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", filePath='" + filePath + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
