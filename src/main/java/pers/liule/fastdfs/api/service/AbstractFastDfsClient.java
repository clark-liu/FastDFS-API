package pers.liule.fastdfs.api.service;


import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import pers.liule.fastdfs.api.constant.FileDfsConstant;
import pers.liule.fastdfs.api.constant.MultiMediaTypeEnum;
import pers.liule.fastdfs.api.domain.FastDfsFileDomain;
import pers.liule.fastdfs.api.dto.FileDTO;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Description: dfs服务抽象类
 * @Author: liule
 * @Date: 2020/8/4 11:21
 */
public abstract class AbstractFastDfsClient {

    private static final Logger logger = LoggerFactory.getLogger(AbstractFastDfsClient.class);

    protected FileDTO upload(MultipartFile multipartFile) {
        NameValuePair[] metaList = new NameValuePair[1];
        String fileName=multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        metaList[0] = new NameValuePair("size", String.valueOf(multipartFile.getSize()));
        String[] uploadResults = null;
        try(InputStream inputStream= multipartFile.getInputStream()) {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024<<4];
            int n;
            while (-1 != (n = inputStream.read(buffer))) {
                bo.write(buffer, 0, n);
            }
            byte[] data = bo.toByteArray();
            uploadResults =  getTrackerClient().upload_file(data,ext,metaList);
        } catch (IOException e) {
            logger.error("IO Exception when upload the file:", e);
        } catch (Exception e) {
            logger.error("Non IO Exception when upload the file:", e);
        }
        if (uploadResults == null) {
            logger.error("upload file fail!");
        }
        if( null!= uploadResults && 1 < uploadResults.length){
            return new FileDTO(fileName,multipartFile.getSize(),getTrackerUrl(),
                    uploadResults[0]+ "/"+uploadResults[1],ext);
        }
        return null;
    }

    protected FileDTO upload(FastDfsFileDomain fastDfsFileDomain) {
        FileDTO fileDTO = null;
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("type", MultiMediaTypeEnum.IMAGE.getTypeName());
        String[] uploadResults = null;
        StorageClient storageClient=null;
        try {
            storageClient = getTrackerClient();
            uploadResults = storageClient.upload_file(fastDfsFileDomain.getContent(), fastDfsFileDomain.getExt(), metaList);
        } catch (IOException e) {
            logger.error("IO Exception when upload the file:", e);
        } catch (Exception e) {
            logger.error("Non IO Exception when upload the file:", e);
        }
        if (uploadResults == null && storageClient!=null ) {
            logger.info("upload file fail, error code: {}",storageClient.getErrorCode());
        }
        if( null != uploadResults && 1 < uploadResults.length){
            fileDTO = new FileDTO(fastDfsFileDomain.getName(),0L,getTrackerUrl(),
                    uploadResults[0]+ "/"+uploadResults[1],null);
        }
        return fileDTO;
    }




    private FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            return storageClient.get_file_info(groupName,remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    private InputStream downFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            return new ByteArrayInputStream(fileByte);
        } catch (IOException e) {
            logger.error("IO Exception: Download File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Download File from Fast DFS failed", e);
        }
        return null;
    }


    protected void deleteFile(String filePath){
        if(StringUtils.isNotEmpty(filePath)){
            try {
                StorageClient storageClient = getTrackerClient();
                String groupName = filePath.split("/",2)[0];
                String remoteFileName = filePath.split("/",2)[1];
                storageClient.delete_file(groupName, remoteFileName);
            }catch (Exception e){
                logger.error("FastDFS delete file fail",e);
            }
        }
    }

    private StorageServer[] getStoreStorages(String groupName) {
        StorageServer[] storageServers = null;
        TrackerClient trackerClient = new TrackerClient();
        try {
            TrackerServer trackerServer = trackerClient.getTrackerServer();
            storageServers = trackerClient.getStoreStorages(trackerServer, groupName);
        }catch (IOException | MyException e){
            logger.error("FastDFS get Storage fail",e);
        }
        return storageServers;
    }

    private ServerInfo[] getFetchStorages(String groupName,
                                                String remoteFileName) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    public String getTrackerUrl(){
        return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port()+"/";
    }

    protected String getFileUrl(String remoteFilePath){
        return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+
                ":"+ClientGlobal.getG_tracker_http_port()+"/"
                +remoteFilePath.split("/",2)[0]
                +"/"+remoteFilePath.split("/",2)[1];
    }

    private static StorageClient getTrackerClient(){
        TrackerServer trackerServer = getTrackerServer();
        return  new StorageClient(trackerServer, null);
    }

    private static TrackerServer getTrackerServer(){
        TrackerClient trackerClient = new TrackerClient();
        try {
            return trackerClient.getTrackerServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getFileByte(String filePath) {
        byte[] result = new byte[]{};
        String groupName = filePath.split("/",2)[0];
        String remoteFileName = filePath.split("/",2)[1];
        try {
            StorageClient storageClient = getTrackerClient();
            return storageClient.download_file(groupName,remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File Byte from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File Byte from Fast DFS failed", e);
        }
        return result;
    }


    /**
     *  bufferedImage 转 byte[]
     * @param bufferedImage
     * @return  byte[]
     */
    private byte[] bufferedImageToByte(BufferedImage bufferedImage) {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try(ImageOutputStream imageOut = ImageIO.createImageOutputStream(bs)) {
            ImageIO.write(bufferedImage, FileDfsConstant.DEFAULT_IMG_FORMAT, imageOut);
            return bs.toByteArray();
        } catch (IOException e) {
            logger.error("BufferedImageToBase64 Upload failed：" , e);
        }
        return new byte[]{};
    }

}
