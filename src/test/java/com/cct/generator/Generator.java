package com.cct.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import com.cct.redmeatojbackend.question.domain.entity.Question;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 生成代码
 */
public class Generator {

    public static void main(String[] args) throws Exception {


        DataModel dataModel = new DataModel();
        dataModel.setModelName("SubmitRecord");
        dataModel.setAuthor("cct");
        dataModel.setModelDesc("提交记录表");
        dataModel.setModuleClassPath("com.cct.redmeatojbackend.question");
        dataModel.setProjectPath("com.cct.redmeatojbackend");
        String inputPathDir = "E:\\012_redMeat_OJ\\redmeatojbackend\\src\\main\\resources\\templates";
        String outputPathDir = "E:\\012_redMeat_OJ\\redmeatojbackend\\src\\main\\java\\com\\cct\\redmeatojbackend\\question";


        Class<Question> Clazz = Question.class;
        Field[] fields = Clazz.getDeclaredFields();
        List<DataModel.Field> fieldArrayList = new ArrayList<>();
        for (Field field : fields) {
            //跳过设置序列化id
            if(field.getName().equals("serialVersionUID")) {
                continue;
            }
            DataModel.Field tmp = new DataModel.Field();
            tmp.setFieldType(field.getType().getSimpleName());
            tmp.setFieldName(field.getName());
            tmp.setFieldTypePath(field.getType().getName());
            fieldArrayList.add(tmp);
        }
        dataModel.setFieldList(fieldArrayList);


        File rootDir = new File(inputPathDir);

        List<File> fileList = FileUtil.loopFiles(rootDir, File::isFile);

        for (File file : fileList) {
            String inputPath = file.getAbsolutePath();
            String outputPath = getOutputPath(outputPathDir, inputPath, dataModel);
            System.out.println(inputPath);
            System.out.println(outputPath);
            doGenerate(inputPath, outputPath, dataModel);
        }
    }

    public static  String getOutputPath(String outputPathDir,String inputPath, DataModel dataModel) {
        int startIndex = inputPath.indexOf("templates");
        startIndex += "templates".length();
        int endIndex = inputPath.lastIndexOf(File.separator)+1;
        String fileName = inputPath.substring(endIndex);
        switch (fileName) {
            case "AddRequest.java.ftl":
                fileName = "Add" + dataModel.getModelName() + "Request.java";
                break;
            case "UpdateRequest.java.ftl":
                fileName = "Update" + dataModel.getModelName() + "Request.java";
                break;
            case "SearchListRequest.java.ftl":
                fileName = "Search" + dataModel.getModelName() + "ListRequest.java";
                break;
            case "SearchRequest.java.ftl":
                fileName = "Search" + dataModel.getModelName() + "Request.java";
                break;
            case   "EntityVo.java.ftl":
                fileName = dataModel.getModelName() + "Vo.java";
                break;
            case "EntityController.java.ftl":
                fileName = dataModel.getModelName() + "Controller.java";
                break;
            case "EntityDao.java.ftl":
                fileName = dataModel.getModelName() + "Dao.java";
                break;
            case "EntityService.java.ftl":
                fileName = dataModel.getModelName() + "Service.java";
                break;
            case "EntityServiceImpl.java.ftl":
                fileName = dataModel.getModelName() + "ServiceImpl.java";
                break;
        }
        return outputPathDir + inputPath.substring(startIndex, endIndex) + fileName;
    }

    /**
     * 生成文件
     *
     * @param inputPath  模板文件输入路径
     * @param outputPath 输出路径
     * @param model      数据模型
     * @throws IOException
     * @throws TemplateException
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模板文件所在的路径
        File templateDir = FileUtil.file(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);

        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 创建模板对象，加载指定模板
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);

        // 生成
        FileWriter fileWriter = new FileWriter(outputPath);
        BufferedWriter out = fileWriter.getWriter(false);
        template.process(model, out);

        // 生成文件后别忘了关闭哦
        out.close();
    }


}
