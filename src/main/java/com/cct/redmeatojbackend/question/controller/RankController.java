package com.cct.redmeatojbackend.question.controller;

import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.common.utils.RespUtils;
import com.cct.redmeatojbackend.question.domain.entity.UserRank;
import com.cct.redmeatojbackend.question.service.RankService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: TODO
 */

@RestController
@RequestMapping("/rank")
public class RankController {

    @Resource
    private RankService rankService;
    @GetMapping("/")
    @ApiOperation("查询提交ac排名")
    BaseResponse<List<UserRank>> searchSubmitRecord(@RequestParam Integer count) {
        List<UserRank> ranks = rankService.getRank(count);
        return RespUtils.success(ranks);
    }
}
