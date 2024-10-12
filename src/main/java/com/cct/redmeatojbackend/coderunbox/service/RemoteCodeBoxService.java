package com.cct.redmeatojbackend.coderunbox.service;

import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 远程代码沙箱调用
 */
public interface RemoteCodeBoxService {
    BaseResponse<RunCodeResp> run(RunCodeReq runCodeReq);
}
