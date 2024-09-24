package com.cct.redmeatojbackend.coderunbox.domain;

import lombok.Data;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 运行代码所需的信息
 */
@Data
public class RunCodeReq {

    String language;

    String code;

    String inputContent;
}
