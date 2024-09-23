package com.cct.redmeatojbackend;

import cn.hutool.core.io.FileUtil;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: TODO
 */
public class Test {
    public static void main(String[] args) {
        // 文件路径
        String filePath = "question/io/1.in";

        // 获取文件后缀
        String suffix = FileUtil.getSuffix(filePath);
        System.out.println("文件后缀: " + suffix);

        // 获取文件前缀
        String prefix = FileUtil.getPrefix(filePath);
        System.out.println("文件前缀: " + prefix);
    }
}
