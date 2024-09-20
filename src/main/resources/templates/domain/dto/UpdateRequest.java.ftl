package ${moduleClassPath}.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
<#assign printed = []>
<#list fieldList as  field>
<#if field.fieldTypePath?? && !(printed?seq_contains(field.fieldTypePath))>
import ${field.fieldTypePath};
<#assign printed += [field.fieldTypePath]>
</#if>
</#list>
/**
* @description ${modelDesc}更新请求
* @author ${author}
*/
@ApiModel(description = "${modelDesc}更新请求")
@Data
public class Update${modelName}Request implements Serializable {

<#list fieldList as field>
<#if field.fieldDesc??>/**
    * ${field.fieldDesc}
    */</#if>
    <#if field.fieldName?? >private ${field.fieldType} ${field.fieldName};</#if>
</#list>

    private static final long serialVersionUID = 1L;
}