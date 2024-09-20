package com.cct.redmeatojbackend.common.domain.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cct.redmeatojbackend.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础分页请求
 * @author cct
 */
@Data
@ApiModel(description = "基础分页请求")
public class BasePageReq implements Serializable {

    @ApiModelProperty("当前页号")
    private int current = 1;


    @ApiModelProperty("每页大小")
    private int pageSize = 10;


    @ApiModelProperty("排序字段")
    private String sortField;


    @ApiModelProperty("排序顺序（默认升序）")
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;

    private static final long serialVersionUID = -5025171285785091800L;

    public Page plusPage() {
        return new Page(current, pageSize);
    }
}
