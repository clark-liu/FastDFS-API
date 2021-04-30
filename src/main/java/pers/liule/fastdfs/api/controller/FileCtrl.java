package pers.liule.fastdfs.api.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.liule.fastdfs.api.common.resp.ResponseWrapper;
import pers.liule.fastdfs.api.common.resp.enums.DfsErrorStatusEnum;
import pers.liule.fastdfs.api.constant.FileFormatEnum;
import pers.liule.fastdfs.api.dto.FileDTO;
import pers.liule.fastdfs.api.service.AbstractFastDfsClient;
import pers.liule.fastdfs.api.validate.ValidUploadFile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 文件服务API
 * @Author: liule
 * @Date: 2020/8/6 14:49
 */
@RestController
@RequestMapping("/api/file")
@Validated
public class FileCtrl extends AbstractFastDfsClient {


    /**
     * 视频类文件上传
     * @param file 视频类文件
     * @return ResponseWrapper
     */
    @PostMapping("/upload/video")
    public ResponseWrapper uploadVideo(@RequestParam("file") @ValidUploadFile(value ={FileFormatEnum.MP4}, maxSize = "100MB") MultipartFile file){
        FileDTO fileDTO = super.upload(file);
        if(fileDTO == null){
          return ResponseWrapper.error(DfsErrorStatusEnum.DFS_SERVICE_OPERATION_ERROR);
        }
        return ResponseWrapper.success(fileDTO);
    }

    /**
     * 图片类文件上传
     * @param file 图片类文件上传
     * @return ResponseWrapper
     */
    @PostMapping("/upload/image")
    public ResponseWrapper uploadImage(@RequestParam("file") @ValidUploadFile(value ={FileFormatEnum.JPG,FileFormatEnum.PNG}, maxSize = "10MB")  MultipartFile file){
        Optional<FileDTO> fileDTO =Optional.ofNullable(super.upload(file));
        return fileDTO.isPresent()?ResponseWrapper.success(fileDTO):ResponseWrapper.error(DfsErrorStatusEnum.DFS_SERVICE_OPERATION_ERROR);
    }

    /**
     * 文本类文件上传
     * @param file 视频类文件
     * @return ResponseWrapper
     */
    @PostMapping("/upload/doc")
    public ResponseWrapper uploadDocument(@RequestParam("file") @ValidUploadFile(value ={FileFormatEnum.PDF}, maxSize = "100MB") MultipartFile file){
        Optional<FileDTO> fileDTO =Optional.ofNullable(super.upload(file));
        return fileDTO.isPresent()?ResponseWrapper.success(fileDTO):ResponseWrapper.error(DfsErrorStatusEnum.DFS_SERVICE_OPERATION_ERROR);
    }

    /**
     * dfs自定义约束器异常捕获处理
     * @param constraintViolationException  自定义约束器异常
     * @return ResponseWrapper
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseWrapper handleConstraintViolation(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        List<String> messages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return ResponseWrapper.error(DfsErrorStatusEnum.DFS_FILE_IS_NOT_VALID,StringUtils.join(messages,","));
    }


}
