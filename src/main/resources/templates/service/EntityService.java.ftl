package ${moduleClassPath}.service;

import ${projectPath}.common.domain.vo.BasePageResp;
import ${projectPath}.common.domain.vo.BaseResponse;
import ${moduleClassPath}.domain.dto.Add${modelName}Request;
import ${moduleClassPath}.domain.dto.Search${modelName}ListRequest;
import ${moduleClassPath}.domain.dto.Search${modelName}Request;
import ${moduleClassPath}.domain.dto.Update${modelName}Request;
import ${moduleClassPath}.domain.vo.${modelName}Vo;

/**
 * @description ${modelDesc}服务层
 * @author ${author}
 */
public interface ${modelName}Service {

    BaseResponse<${modelName}Vo> add${modelName}(Add${modelName}Request add${modelName}Request);

    BaseResponse<Void> delete${modelName}(Long questionId);

    BaseResponse<${modelName}Vo> update${modelName}(Update${modelName}Request update${modelName}Request);

    BaseResponse<${modelName}Vo> search${modelName}(Search${modelName}Request search${modelName}Request);

    BaseResponse<BasePageResp<${modelName}Vo>> search${modelName}Page(Search${modelName}ListRequest search${modelName}ListRequest);
}
