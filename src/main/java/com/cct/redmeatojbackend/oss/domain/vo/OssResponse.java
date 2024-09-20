package com.cct.redmeatojbackend.oss.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: oss响应
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OssResponse {
    @ApiModelProperty(value = "上传的临时url")
    private String uploadUrl;

    @ApiModelProperty(value = "成功后能够下载的url")
    private String downloadUrl;
}
