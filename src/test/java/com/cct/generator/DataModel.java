package com.cct.generator;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 代码生成所用数据模型
 */
@Data
@ToString
public class DataModel {

    /**
     * 首字母要大写，使生成代码符合驼峰式名民规范
     */
    public String modelName;

    /**
     * 表的中文描述
     */
    public String modelDesc;

    /**
     * 当前模块类路径
     */
    public String moduleClassPath;

    /**
     * 项目路径
     */
    public String projectPath = "com.cct.redmeatojbackend";


    /**
     * 作者
     */
    public String author;


    /**
     * 生成dto,vo对象所需的数据模型
     */
    public List<Field> fieldList;

    @Data
    @ToString
    public static class Field {

        /**
         * 字段名
         */
        public String fieldName;

        /**
         * 字段类型
         */
        public String fieldType;

        /**
         * 字段描述
         */
        public String fieldDesc;

        /**
         * 字段类型全类名
         */
        public String fieldTypePath;
    }
}
