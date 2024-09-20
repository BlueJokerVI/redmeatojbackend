package com.cct.redmeatojbackend.common.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BeintsProject: redmeatojbackend
 * @Author: cct
 * @Description: 基础分页返回类
 */
@ApiModel(description = "基础分页响应")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePageResp<T> {

    @ApiModelProperty(value = "当前页")
    Integer current;
    @ApiModelProperty(value = "页大小")
    Integer pageSize;
    @ApiModelProperty(value = "一共多少页")
    Integer total;
    @ApiModelProperty(value = "是否最后一页")
    Boolean isLastPage;
    @ApiModelProperty(value = "数据列表")
    List<T> lists;


    public static <T> BasePageResp<T> init(Integer current, Integer pageSize, Integer total, Boolean isLastPage, List<T> list) {
        return new BasePageResp<>(current, pageSize, total, isLastPage, list);
    }

    public static <T> BasePageResp<T> init(Page resp) {
        boolean last = isLast(resp.getCurrent(), resp.getSize(), resp.getTotal());
        return init((int)resp.getCurrent(), (int)resp.getSize(), (int)resp.getTotal(), last , resp.getRecords());
    }


    public <R> BasePageResp<R> toVo(BasePageResp<T> basePageResp,Class<R> voClass){
        BasePageResp<R> vo = new BasePageResp<>();
        List<T> source = basePageResp.getLists();
        List<R> targetList = BeanUtil.copyToList(source,voClass);
        vo.setLists(targetList);
        vo.setPageSize(getPageSize());
        vo.setIsLastPage(getIsLastPage());
        vo.setTotal(getTotal());
        vo.setCurrent(getCurrent());
        return vo;
    }

    /**
     * 判断是否是最后一页
     * @param current
     * @param pageSize
     * @param total
     * @return
     */
    private static boolean isLast(long current, long pageSize, long total) {
        long num = (current - 1) * pageSize;
        return total - num <= pageSize;
    }
}
