package ${moduleClassPath}.domain.dto;
import cn.hutool.core.bean.BeanUtil;
import ${moduleClassPath}.domain.entity.${modelName};
<#assign printed = []>
<#list fieldList as  field>
<#if field.fieldTypePath?? && !(printed?seq_contains(field.fieldTypePath))>
import ${field.fieldTypePath};
<#assign printed += [field.fieldTypePath]>
</#if>
</#list>
<#macro lowerFirstChar modelName>${modelName?substring(0, 1)?lower_case}${modelName?substring(1)}</#macro>
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
/**
 * @description ${modelDesc}添加请求
 * @author ${author}
 */
@Data
@ApiModel(description = "${modelDesc}添加请求")
public class Add${modelName}Request implements Serializable {

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