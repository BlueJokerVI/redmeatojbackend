package com.cct.redmeatojbackend.oss.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 上传文件请求入参
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssRequest {

    /**
     * 要求以  test/img/xxx 的形式不要以/开头
     */
    @ApiModelProperty(value = "文件存储路径")
    @NotNull
    private String filePath;

    @ApiModelProperty(value = "文件名")
    @NotNull
    @Size(min = 3,max = 100)
    private String fileName;

    @ApiModelProperty(value = "自动生成地址")
    @Builder.Default
    private boolean autoPath = true;
}
