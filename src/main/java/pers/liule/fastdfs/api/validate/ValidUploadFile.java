package pers.liule.fastdfs.api.validate;


import pers.liule.fastdfs.api.constant.FileFormatEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 上传文件注解
 * @Author: liule
 * @Date: 2020/8/7 14:14
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartFileValidator.class)
public @interface ValidUploadFile {

    String DEFAULT_MAXSIZE = "-1";

    /**
     * 允许上传的格式
     */
    FileFormatEnum[] value();

    /**
     * 文件后缀是否区分大小写
     */
    boolean ignoreCase() default true;

    /**
     * 上传的文件是否允许为空
     */
    boolean allowEmpty() default false;

    /**
     * 默认不限制但必须小于等于配置文件的数据
     */
    String maxSize() default DEFAULT_MAXSIZE;

    String minSize() default "0";

    String message() default "The upload file is not verified.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
