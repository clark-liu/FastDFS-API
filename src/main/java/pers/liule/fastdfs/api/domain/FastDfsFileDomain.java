package pers.liule.fastdfs.api.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Description: fastDFS 实体类
 * @Author: liule
 * @Date: 2020/4/1
 */
public class FastDfsFileDomain implements Serializable {

    private static final long serialVersionUID = -5357549305957584298L;

    private String name;

    private byte[] content;

    private String ext;

    private String md5;

    private String createBy;

    private String remoteFilePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getRemoteFilePath() {
        return remoteFilePath;
    }

    public void setRemoteFilePath(String remoteFilePath) {
        this.remoteFilePath = remoteFilePath;
    }

    public FastDfsFileDomain(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "FastDFSFile{" +
                "name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                ", ext='" + ext + '\'' +
                ", md5='" + md5 + '\'' +
                ", createBy='" + createBy + '\'' +
                ", remoteFilePath='" + remoteFilePath + '\'' +
                '}';
    }
}
