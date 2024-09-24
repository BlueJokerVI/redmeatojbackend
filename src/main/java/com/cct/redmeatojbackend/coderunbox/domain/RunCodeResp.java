package com.cct.redmeatojbackend.coderunbox.domain;

import lombok.Data;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 运行代码结果封装类
 */
@Data
public class RunCodeResp {

    /**
     * 运行输出
     */
    String outputContext;

    /**
     * 运行结果编号
     */
    Integer code;

    /**
     * 运行结果描述
     */
    String result;
}
