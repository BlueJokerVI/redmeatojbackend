package com.cct.redmeatojbackend.common.domain.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 应用类型
 * @Version: 1.0
 */
@Getter
public enum UserRoleEnum {
    /**
     * 普通用户：0、管理员：1、黑名单用户：2
     */
    COMMON_USER(0, "普通用户"),
    ADMIN_USER(1, "管理员"),
    BLACK_USER(2, "黑名单用户"),
    VIP_USER(3, "VIP用户");

    final int code;
    final String desc;

    UserRoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据 code 值获取枚举实例
     *
     * @param code 枚举的 code 值
     * @return 对应的枚举实例，如果未找到则返回 null
     */
    public static UserRoleEnum getByCode(int code) {
        for (UserRoleEnum status : UserRoleEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取所有枚举的 code 列表
     *
     * @return 枚举的 code 列表
     */
    public static List<Integer> getAllCodes() {
        List<Integer> codes = new ArrayList<>();
        for (UserRoleEnum status : UserRoleEnum.values()) {
            codes.add(status.getCode());
        }
        return codes;
    }
}
