package com.cct.redmeatojbackend.question.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.cct.redmeatojbackend.common.constant.OssConstant;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.domain.vo.BasePageResp;
import com.cct.redmeatojbackend.common.domain.vo.BaseResponse;
import com.cct.redmeatojbackend.common.exception.BusinessException;
import com.cct.redmeatojbackend.common.utils.RespUtils;
import com.cct.redmeatojbackend.oss.MinIOTemplate;
import com.cct.redmeatojbackend.question.domain.dto.GetTestCasePageRequest;
import com.cct.redmeatojbackend.question.domain.entity.TestCase;
import com.cct.redmeatojbackend.question.service.QuestionIOService;
import io.minio.Result;
import io.minio.messages.Item;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 问题测试用例IO服务
 */
@Service
public class QuestionIOServiceImpl implements QuestionIOService {

    @Resource
    private MinIOTemplate minIOTemplate;

    @Override
    public boolean upLoadQuestionIOFile(Long questionId, TestCase testCase) {
        String ossPathIn = OssConstant.QUESTION_IO_PREFIX_PATH + questionId + "/" + String.format("%d", testCase.getTestCaseId()) + ".in";
        String ossPathOut = OssConstant.QUESTION_IO_PREFIX_PATH + questionId + "/" + String.format("%d", testCase.getTestCaseId()) + ".out";
        minIOTemplate.putObjectWithString(OssConstant.OSS_BUCKET, ossPathIn, testCase.getInputContent());
        minIOTemplate.putObjectWithString(OssConstant.OSS_BUCKET, ossPathOut, testCase.getOutputContent());
        return true;
    }


    @Override
    public BaseResponse<BasePageResp<TestCase>> getTestCasePage(GetTestCasePageRequest getTestCasePageRequest) {

        Long questionId = getTestCasePageRequest.getQuestionId();
        int current = getTestCasePageRequest.getCurrent();
        int pageSize = getTestCasePageRequest.getPageSize();
        //计算要返回起始与结束的测试用例编号
        int start = (current - 1) * pageSize + 1;
        int end = current * pageSize;
        int total = 0;

        //questionPrefixPath一定要以"/"结尾指定某个文件夹，如/xxx/aaa/bbb/
        //  /xxx/aaa/bbb 等价与 /xxx/aaa/
        String questionPrefixPath = OssConstant.QUESTION_IO_PREFIX_PATH + questionId + "/";

        Iterable<Result<Item>> results = minIOTemplate.listObjects(OssConstant.OSS_BUCKET, questionPrefixPath, true);

        //<文件前缀名，TestCaseVo>
        Map<String, TestCase> mp = new HashMap<>();
        //获取问题对应存在的所有IO测试用例
        for (Result<Item> itemResult : results) {
            Item item;
            try {
                item = itemResult.get();
            } catch (Exception e) {
                throw new BusinessException(RespCodeEnum.SYSTEM_ERROR, "获取测试用例失败！");
            }
            total++;
            String fileName = FileUtil.getName(item.objectName());
            String fileSuffix = FileUtil.getSuffix(fileName);
            String filePrefix = FileUtil.getPrefix(fileName);
            int id = Integer.parseInt(filePrefix);
            //封装当前分页内数据
            if (id >= start && id <= end) {
                TestCase testCase = mp.get(filePrefix);
                if (testCase == null) {
                    testCase = new TestCase();
                    getFileContext(item, fileSuffix, testCase);
                    testCase.setTestCaseId(Integer.parseInt(filePrefix));
                    mp.put(filePrefix, testCase);
                } else {
                    getFileContext(item, fileSuffix, testCase);
                }
            }
        }
        total /= 2;
        List<TestCase> testCases = new ArrayList<>(mp.values());
        //分页响应封装
        BasePageResp<TestCase> testCaseVoBasePageResp = BasePageResp.init(current, pageSize, total, testCases);
        return RespUtils.success(testCaseVoBasePageResp);
    }

    /**
     * 获取测试用例内容
     *
     * @param item
     * @param fileSuffix
     * @param testCase
     */
    private void getFileContext(Item item, String fileSuffix, TestCase testCase) {
        InputStream inputStream = minIOTemplate.getObject(OssConstant.OSS_BUCKET, item.objectName());
        if (fileSuffix.equals(OssConstant.TEST_CASE_INPUT_SUFFIX_NAME)) {
            testCase.setInputContent(IoUtil.readUtf8(inputStream));
        }
        if (fileSuffix.equals(OssConstant.TEST_CASE_OUTPUT_SUFFIX_NAME)) {
            testCase.setOutputContent(IoUtil.readUtf8(inputStream));
        }
    }
}