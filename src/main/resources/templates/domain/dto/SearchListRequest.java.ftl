package ${moduleClassPath}.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${projectPath}.common.domain.dto.BasePageReq;
import ${moduleClassPath}.domain.entity.${modelName};
import lombok.Data;
import java.io.Serializable;
<#assign printed = []>
<#list fieldList as  field>
<#if field.fieldTypePath?? && !(printed?seq_contains(field.fieldTypePath))>
import ${field.fieldTypePath};
<#assign printed += [field.fieldTypePath]>
</#if>
</#list>
<#macro lowerFirstChar modelName>${modelName?substring(0, 1)?lower_case}${modelName?substring(1)}</#macro>
/**
* @description ${modelDesc}分页查询请求
* @author ${author}
*/
@ApiModel(description = "${modelDesc}分页查询请求")
import io.swagger.annotations.ApiModel;
@Data
public class Search${modelName}ListRequest extends BasePageReq implements Serializable {

<#list fieldList as field>
    <#if field.fieldDesc??>/**
    * ${field.fieldDesc}
    */</#if>
    <#if field.fieldName?? >private ${field.fieldType} ${field.fieldName};</#if>
</#list>


    public ${modelName} to${modelName}(){
        ${modelName} <@lowerFirstChar modelName = modelName /> = new ${modelName}();
        BeanUtil.copyProperties(this,<@lowerFirstChar modelName = modelName />);
        return <@lowerFirstChar modelName = modelName />;
    }

    @Override
    public Page<${modelName}> plusPage() {
        return new Page<>();
    }

    private static final long serialVersionUID = 1L;
}