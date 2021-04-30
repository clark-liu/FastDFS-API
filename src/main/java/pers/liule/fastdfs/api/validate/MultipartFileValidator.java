package pers.liule.fastdfs.api.validate;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import pers.liule.fastdfs.api.common.resp.enums.DfsErrorStatusEnum;
import pers.liule.fastdfs.api.constant.FileDfsConstant;
import pers.liule.fastdfs.api.constant.FileFormatEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Description: dfs文件管理自定义校验器
 * @Author: liule
 * @Date: 2020/8/7 14:15
 */
public class MultipartFileValidator implements ConstraintValidator<ValidUploadFile, MultipartFile> {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String commonMaxRequestSize;

    private ValidUploadFile validUploadFile;

    private long maxSize = -1;

    private long minSize = 0;

    private List<String> extension = new ArrayList<>();

    @Override
    public void initialize(ValidUploadFile validFile) {
        this.validUploadFile = validFile;
        FileFormatEnum[] fileTypes = validUploadFile.value();
        for (FileFormatEnum fileFormat : fileTypes) {
            extension.add(fileFormat.getType());
        }
        if (validFile.maxSize().equals(ValidUploadFile.DEFAULT_MAXSIZE)) {
            this.maxSize = parseSize(commonMaxRequestSize);
        } else {
            this.maxSize = parseSize(validFile.maxSize());
        }
        this.minSize = parseSize(validFile.minSize());
    }

    private void validMessage(String message, ConstraintValidatorContext cvc) {
        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private long parseSize(String size) {
        if(StringUtils.isEmpty(size)){
            return 0L;
        }
        size = size.toUpperCase(Locale.ENGLISH);
        if(!StringUtils.equals(size, FileDfsConstant.FILE_MIN_SIZE)){
            Long formatSize = Long.valueOf(size.substring(0, size.length() - 2));
            if (size.endsWith(FileDfsConstant.KILO_BYTE)) {
                return formatSize << 10;
            }else if (size.endsWith(FileDfsConstant.MEGA_BYTE)) {
                return formatSize << 20;
            }else {
                return 0L;
            }
        }
        return Long.valueOf(size);
    }

    /**
     * 校验逻辑
     * @param multipartFile 文件
     * @param constraintValidatorContext  自定义校验器上下文
     * @return boolean
     */
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile.isEmpty()) {
            if (validUploadFile.allowEmpty()) {
                return true;
            }
            validMessage(DfsErrorStatusEnum.DFS_FILE_IS_NULL.getMessage(),constraintValidatorContext);
            return false;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            validMessage(DfsErrorStatusEnum.DFS_FILE_IS_NULL.getMessage(),constraintValidatorContext);
            return false;
        }
        if (validUploadFile.ignoreCase()) {
            originalFilename = originalFilename.toLowerCase();
        }
        if (!extension.isEmpty() && extension.stream().noneMatch(originalFilename::endsWith)) {
            validMessage(DfsErrorStatusEnum.DFS_FILE_FORMAT_ILLEGAL.getMessage(),constraintValidatorContext);
            return false;
        }
        long size = multipartFile.getSize();
        if (size < this.minSize) {
            validMessage(DfsErrorStatusEnum.DFS_FILE_SIZE_MORE_THEN_MIN.getMessage(), constraintValidatorContext);
            return false;
        }
        if (size > this.maxSize) {
            validMessage(DfsErrorStatusEnum.DFS_FILE_SIZE_LESS_THEN_MIN.getMessage(), constraintValidatorContext);
            return false;
        }
        return true;
    }


}
