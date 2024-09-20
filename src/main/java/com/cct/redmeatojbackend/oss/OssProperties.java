package com.cct.redmeatojbackend.oss;

import com.cct.redmeatojbackend.oss.domain.enums.OssType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cct
 * @description 自定义starter可以配置的属性
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    /**
     * 是否开启
     */
    Boolean enabled;

    /**
     * 存储对象服务器类型
     */
    OssType type;

    /**
     * OSS 访问端点，集群时需提供统一入口
     */
    String endpoint;

    /**
     * 用户名
     */
    String accessKey;

    /**
     * 密码
     */
    String secretKey;

    /**
     * 存储桶
     */
    String bucketName;
}