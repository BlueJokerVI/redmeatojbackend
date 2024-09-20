package ${moduleClassPath}.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${moduleClassPath}.dao.mapper.${modelName}Mapper;
import ${moduleClassPath}.domain.dto.Search${modelName}Request;
import ${moduleClassPath}.domain.entity.${modelName};
import org.springframework.stereotype.Repository;
<#macro upperFirstChar modelName>${modelName?substring(0, 1)?upper_case}${modelName?substring(1)}</#macro>

/**
* @description ${modelDesc}数据库操作层
* @author ${author}
*/
@Repository
public class ${modelName}Dao extends ServiceImpl<${modelName}Mapper, ${modelName}> {


    public ${modelName} search${modelName}(Search${modelName}Request search${modelName}Request) {
        return lambdaQuery().select()
<#list fieldList as field><#if field.fieldName??>                            .eq(search${modelName}Request.get<@upperFirstChar modelName=field.fieldName />() != null, ${modelName}::get<@upperFirstChar modelName=field.fieldName />, search${modelName}Request.get<@upperFirstChar modelName=field.fieldName />())
</#if></#list>
                            .last("limit 1").one();
    }
}




