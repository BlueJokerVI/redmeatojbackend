package ${moduleClassPath}.domain.dto;
import cn.hutool.core.bean.BeanUtil;
import ${moduleClassPath}.domain.entity.${modelName};
import lombok.Data;
import java.io.Serializable;
<#assign printed = []>
<#list fieldList as  field>
<#if field.fieldTypePath?? && !(printed?seq_contains(field.fieldTypePath))>
import ${field.fieldTypePath};
import io.swagger.annotations.ApiModel;
<#assign printed += [field.fieldTypePath]>
</#if>
</#list>
<#macro lowerFirstChar modelName>${modelName?substring(0, 1)?lower_case}${modelName?substring(1)}</#macro>
/**
* @description ${modelDesc}查询请求
* @author ${author}
*/
@ApiModel(description = "${modelDesc}查询请求")
@Data
public class Search${modelName}Request implements Serializable {
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

    private static final long serialVersionUID = 1L;
}