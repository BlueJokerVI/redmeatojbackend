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
import io.swagger.annotations.Api;
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
@Api(tags = "${modelDesc?substring(0, modelDesc?length - 1)}控制器")
public class ${modelName}Controller {

    @Resource
    ${modelName}Service <@lowerFirstChar modelName=modelName />Service;

    @PostMapping("/add")
    @ApiOperation("${modelDesc?substring(0, modelDesc?length - 1)}添加")
    BaseResponse<${modelName}Vo>  add${modelName}(@Valid @RequestBody Add${modelName}Request add${modelName}Request){
        return <@lowerFirstChar modelName=modelName />Service.add${modelName}(add${modelName}Request);
    }

    @GetMapping("/delete")
    @ApiOperation("${modelDesc?substring(0, modelDesc?length - 1)}删除")
    BaseResponse<Void>  delete${modelName}(@Valid @NotNull @RequestParam Long <@lowerFirstChar modelName=modelName />Id){
        return <@lowerFirstChar modelName=modelName />Service.delete${modelName}(<@lowerFirstChar modelName=modelName />Id);
    }

    @PostMapping("/update")
    @ApiOperation("${modelDesc?substring(0, modelDesc?length - 1)}更新")
    BaseResponse<${modelName}Vo>  update${modelName}(@Valid @RequestBody Update${modelName}Request update${modelName}Request){
        return <@lowerFirstChar modelName=modelName />Service.update${modelName}(update${modelName}Request);
    }

    @PostMapping("/search")
    @ApiOperation("${modelDesc?substring(0, modelDesc?length - 1)}查询")
    BaseResponse<${modelName}Vo>  search${modelName}(@Valid @RequestBody Search${modelName}Request search${modelName}Request){
        return <@lowerFirstChar modelName=modelName />Service.search${modelName}(search${modelName}Request);
    }

    @PostMapping("/searchPage")
    @ApiOperation("${modelDesc?substring(0, modelDesc?length - 1)}分页查询")
    BaseResponse<BasePageResp<${modelName}Vo>> search${modelName}Page(@Valid @RequestBody Search${modelName}ListRequest search${modelName}ListRequest){
        return <@lowerFirstChar modelName=modelName />Service.search${modelName}Page(search${modelName}ListRequest);
    }

}
