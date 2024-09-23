package ${moduleClassPath}.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${projectPath}.common.domain.enums.RespCodeEnum;
import ${projectPath}.common.domain.vo.BasePageResp;
import ${projectPath}.common.domain.vo.BaseResponse;
import ${projectPath}.common.utils.RespUtils;
import ${projectPath}.common.utils.ThrowUtils;
import ${moduleClassPath}.dao.${modelName}Dao;
import ${moduleClassPath}.dao.mapper.${modelName}Mapper;
import ${moduleClassPath}.domain.dto.Add${modelName}Request;
import ${moduleClassPath}.domain.dto.Search${modelName}ListRequest;
import ${moduleClassPath}.domain.dto.Search${modelName}Request;
import ${moduleClassPath}.domain.dto.Update${modelName}Request;
import ${moduleClassPath}.domain.entity.${modelName};
import ${moduleClassPath}.domain.vo.${modelName}Vo;
import ${moduleClassPath}.service.${modelName}Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
<#macro lowerFirstChar modelName>${modelName?substring(0, 1)?lower_case}${modelName?substring(1)}</#macro>
<#macro upperFirstChar modelName>${modelName?substring(0, 1)?upper_case}${modelName?substring(1)}</#macro>
/**
 * @description ${modelDesc}服务层实现类
 * @author ${author}
 */
@Service
public class ${modelName}ServiceImpl implements ${modelName}Service {

    @Resource
    private ${modelName}Dao <@lowerFirstChar modelName = modelName />Dao;

    @Resource
    private ${modelName}Mapper <@lowerFirstChar modelName = modelName />Mapper;

    @Override
    public BaseResponse<${modelName}Vo> add${modelName}(Add${modelName}Request add${modelName}Request) {
        ${modelName} <@lowerFirstChar modelName = modelName /> = add${modelName}Request.to${modelName}();
        <@lowerFirstChar modelName = modelName />.setId(IdUtil.getSnowflakeNextId());
        ThrowUtils.throwIf(!<@lowerFirstChar modelName = modelName />Dao.save(<@lowerFirstChar modelName = modelName />), RespCodeEnum.OPERATION_ERROR, "添加${modelDesc?substring(0, modelDesc?length - 1)}失败");
        return RespUtils.success(${modelName}Vo.toVo(<@lowerFirstChar modelName = modelName />));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BaseResponse<Void> delete${modelName}(Long <@lowerFirstChar modelName = modelName />Id) {
        ThrowUtils.throwIf(<@lowerFirstChar modelName = modelName />Dao.getById(<@lowerFirstChar modelName = modelName />Id) == null, RespCodeEnum.OPERATION_ERROR, "${modelDesc?substring(0, modelDesc?length - 1)}不存在");
        ThrowUtils.throwIf(!<@lowerFirstChar modelName = modelName />Dao.removeById(<@lowerFirstChar modelName = modelName />Id), RespCodeEnum.OPERATION_ERROR, "删除${modelDesc?substring(0, modelDesc?length - 1)}失败");
        return RespUtils.success();
    }

    @Override
    public BaseResponse<${modelName}Vo> update${modelName}(Update${modelName}Request update${modelName}Request) {
        ${modelName} old${modelName} = <@lowerFirstChar modelName = modelName />Dao.getById(update${modelName}Request);
        ThrowUtils.throwIf(old${modelName} == null, RespCodeEnum.OPERATION_ERROR, "${modelDesc?substring(0, modelDesc?length - 1)}不存在");
        BeanUtil.copyProperties(update${modelName}Request, old${modelName}, CopyOptions.create().setIgnoreNullValue(true));
        ThrowUtils.throwIf(!<@lowerFirstChar modelName = modelName />Dao.updateById(old${modelName}), RespCodeEnum.OPERATION_ERROR, "更新${modelDesc?substring(0, modelDesc?length - 1)}失败");
        return RespUtils.success(${modelName}Vo.toVo(old${modelName}));
    }

    @Override
    public BaseResponse<${modelName}Vo> search${modelName}(Search${modelName}Request search${modelName}Request) {
        ${modelName} <@lowerFirstChar modelName = modelName /> = <@lowerFirstChar modelName = modelName />Dao.search${modelName}(search${modelName}Request);
        return RespUtils.success(${modelName}Vo.toVo(<@lowerFirstChar modelName = modelName />));
    }

    @Override
    public BaseResponse<BasePageResp<${modelName}Vo>> search${modelName}Page(Search${modelName}ListRequest search${modelName}ListRequest) {
        LambdaQueryWrapper<${modelName}> wrapper = new LambdaQueryWrapper<>();
        wrapper.select()
<#list fieldList as field><#if field.fieldName??>
               .eq(search${modelName}ListRequest.get<@upperFirstChar modelName = field.fieldName />() != null, ${modelName}::get<@upperFirstChar modelName = field.fieldName />, search${modelName}ListRequest.get<@upperFirstChar modelName = field.fieldName />())</#if><#if field?index + 1 != fieldList?size>
</#if></#list>;
        Page<${modelName}> <@lowerFirstChar modelName = modelName />Page = <@lowerFirstChar modelName = modelName />Mapper.selectPage(search${modelName}ListRequest.plusPage(), wrapper);
        BasePageResp<${modelName}> basePageResp = BasePageResp.init(<@lowerFirstChar modelName = modelName />Page);
        return RespUtils.success(basePageResp.toVo(basePageResp, ${modelName}Vo.class));
    }
}
