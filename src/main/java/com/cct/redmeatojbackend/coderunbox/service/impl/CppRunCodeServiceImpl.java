package com.cct.redmeatojbackend.coderunbox.service.impl;

import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.coderunbox.service.RunCodeService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: todo Cpp运行代码服务
 */
@Service
@Validated
public class CppRunCodeServiceImpl implements RunCodeService {
    @Override
    public boolean support(String language) {
        return false;
    }

    @Override
    public RunCodeResp run(@Valid RunCodeReq runCodeReq){
        return new RunCodeResp();
    }



}
