package com.cct.redmeatojbackend.user.domain.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 性别
 * @Version: 1.0
 */
@Getter
public enum GenderEnum {
    /**
     *
     */
    REVIEW(0, "男"),
    REFUSE(1,"女"),
    PASS(2,"其他");

    final int code;
    final String desc;

    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    /**
     * 根据 code 值获取枚举实例
     * @param code 枚举的 code 值
     * @return 对应的枚举实例，如果未找到则返回 null
     */
    public static GenderEnum getByCode(int code) {
        for (GenderEnum status : GenderEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取所有枚举的 code 列表
     * @return 枚举的 code 列表
     */
    public static List<Integer> getAllCodes() {
        List<Integer> codes = new ArrayList<>();
        for (GenderEnum status : GenderEnum.values()) {
            codes.add(status.getCode());
        }
        return codes;
    }
}
