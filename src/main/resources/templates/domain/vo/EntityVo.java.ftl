package ${moduleClassPath}.domain.vo;

import cn.hutool.core.bean.BeanUtil;
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
* @description ${modelDesc}视图
* @author ${author}
*/
@Data
public class ${modelName}Vo implements Serializable {

<#list fieldList as field>
    <#if field.fieldDesc??>/**
    * ${field.fieldDesc}
    */</#if>
    <#if field.fieldName?? >private ${field.fieldType} ${field.fieldName};</#if>
</#list>

    public static ${modelName}Vo toVo(${modelName} <@lowerFirstChar modelName = modelName />) {
        ${modelName}Vo <@lowerFirstChar modelName = modelName />Vo = new ${modelName}Vo();
        BeanUtil.copyProperties(<@lowerFirstChar modelName = modelName />,<@lowerFirstChar modelName = modelName />Vo);
        return <@lowerFirstChar modelName = modelName />Vo;
    }


    private static final long serialVersionUID = 1L;
}