package com.cct.redmeatojbackend.coderunbox.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 语言枚举类
 */
@AllArgsConstructor
@Getter
public enum LanguageEnum {
    /**
     *
     */
    JAVA("java"),
    CPP("cpp"),
    C("c");

    private final String language;
}
