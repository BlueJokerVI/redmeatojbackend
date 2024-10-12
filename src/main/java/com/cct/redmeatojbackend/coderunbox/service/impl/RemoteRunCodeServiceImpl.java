package com.cct.redmeatojbackend.coderunbox.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeReq;
import com.cct.redmeatojbackend.coderunbox.domain.RunCodeResp;
import com.cct.redmeatojbackend.coderunbox.service.RemoteCodeBoxService;
import com.cct.redmeatojbackend.coderunbox.service.RemoteCodeBoxUrlService;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 调用远程代码沙箱服务，远程代码沙箱可以有多个，使用一致性hash环让请求负载均衡
 */

@Service
public class RemoteRunCodeServiceImpl implements RemoteCodeBoxService {

    /**
     * 定义鉴权请求头和密钥
     */
    @Value("${codebox.auth.header}")
    private String authRequestHeader;

    @Value("${codebox.auth.secret}")
    private String authRequestSecret;

    @Resource
    private RemoteCodeBoxUrlService remoteCodeBoxUrlService;

    @Resource
    private UserService userService;

    @Override
    public BaseResponse<RunCodeResp> run(RunCodeReq runCodeReq) {

        //使用用户id作为hash的key值获取远程代码沙箱url
        Long id = userService.getCurrentUser().getId();
        String url = remoteCodeBoxUrlService.getServerUrl(id.toString());

        String json = JSONUtil.toJsonStr(runCodeReq);
        //链式构建请求
        String resp = HttpRequest.post(url)
                .header(authRequestHeader, authRequestSecret)
                //请求题
                .body(json)
                //设置超时时间，毫秒
                .timeout(30000)
                .execute()
                .body();
        return JSONUtil.toBean(resp, new TypeReference<BaseResponse<RunCodeResp>>() {},false);
    }
}
