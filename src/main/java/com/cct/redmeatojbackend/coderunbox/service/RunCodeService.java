package com.cct.redmeatojbackend.coderunbox.service;

import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;

import javax.validation.Valid;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 运行代码服务
 * @Version: 1.0
 */
public interface RunCodeService {


    boolean support(String language);


    /**
     * 运行代码
     * @param runCodeReq
     * @return
     */
    RunCodeResp run(@Valid RunCodeReq runCodeReq);
}
