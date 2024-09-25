package com.cct.redmeatojbackend.coderunbox.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.coderunbox.domain.enums.RunCodeResultEnum;
import com.cct.redmeatojbackend.coderunbox.service.RunCodeService;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.Paths;

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
