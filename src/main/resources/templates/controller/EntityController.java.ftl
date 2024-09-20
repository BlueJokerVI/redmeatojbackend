package ${moduleClassPath}.controller;

import ${projectPath}.common.domain.vo.BasePageResp;
import ${projectPath}.common.domain.vo.BaseResponse;
import ${moduleClassPath}.domain.dto.Add${modelName}Request;
import ${moduleClassPath}.domain.dto.Search${modelName}ListRequest;
import ${moduleClassPath}.domain.dto.Search${modelName}Request;
import ${moduleClassPath}.domain.dto.Update${modelName}Request;
import ${moduleClassPath}.domain.vo.${modelName}Vo;
import ${moduleClassPath}.service.${modelName}Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
<#macro lowerFirstChar modelName>${modelName?substring(0, 1)?lower_case}${modelName?substring(1)}</#macro>
/**
 * @description ${modelDesc}控制器
 * @author ${author}
 */
@RestController
@RequestMapping("/<@lowerFirstChar modelName=modelName />")
public class ${modelName}Controller {

    @Resource
    ${modelName}Service <@lowerFirstChar modelName=modelName />Service;

    @PostMapping("/add")
    BaseResponse<${modelName}Vo>  add${modelName}(@Valid @RequestBody Add${modelName}Request add${modelName}Request){
        return <@lowerFirstChar modelName=modelName />Service.add${modelName}(add${modelName}Request);
    }

    @GetMapping("/delete")
    BaseResponse<Void>  delete${modelName}(@Valid @NotNull @RequestParam Long <@lowerFirstChar modelName=modelName />Id){
        return <@lowerFirstChar modelName=modelName />Service.delete${modelName}(<@lowerFirstChar modelName=modelName />Id);
    }

    @PostMapping("/update")
    BaseResponse<${modelName}Vo>  update${modelName}(@Valid @RequestBody Update${modelName}Request update${modelName}Request){
        return <@lowerFirstChar modelName=modelName />Service.update${modelName}(update${modelName}Request);
    }

    @PostMapping("/search")
    BaseResponse<${modelName}Vo>  search${modelName}(@Valid @RequestBody Search${modelName}Request search${modelName}Request){
        return <@lowerFirstChar modelName=modelName />Service.search${modelName}(search${modelName}Request);
    }

    @PostMapping("/searchPage")
    BaseResponse<BasePageResp<${modelName}Vo>> search${modelName}Page(@Valid @RequestBody Search${modelName}ListRequest search${modelName}ListRequest){
        return <@lowerFirstChar modelName=modelName />Service.search${modelName}Page(search${modelName}ListRequest);
    }

}
