package com.cct.redmeatojbackend.coderunbox.service;

import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 责任链模式实现不同代码运行容器的调用
 */
@Service
public class RunCodeServiceManagerChain {

    private final List<RunCodeService> runCodeServiceList;

    @Autowired
    public RunCodeServiceManagerChain(List<RunCodeService> runCodeServiceList) {
        this.runCodeServiceList = runCodeServiceList;
    }

    public RunCodeResp runCode(RunCodeReq runCodeReq) {
        for (RunCodeService runCodeService : runCodeServiceList) {
            if (runCodeService.support(runCodeReq.getLanguage())) {
                return runCodeService.run(runCodeReq);
            }
        }
        throw new BusinessException(RespCodeEnum.PARAMS_ERROR, "不支持该语言运行");
    }



}
