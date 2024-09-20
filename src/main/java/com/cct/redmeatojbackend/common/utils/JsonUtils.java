package com.cct.redmeatojbackend.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Description:Json工具类
 */
public class JsonUtils {

    public static ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * 将JSON字符串转换为指定类型的对象
     * @param str
     * @param clz
     * @return
     * @param <T>
     */
    public static <T> T toObj(String str, Class<T> clz) {
        try {
            return jsonMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 将JSON字符串转换为指定类型的对象，支持复杂类型
     * @param str
     * @param clz
     * @return
     * @param <T>
     */
    public static <T> T toObj(String str, TypeReference<T> clz) {
        try {
            return jsonMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 将JSON字符串转换为指定类型的列表
     * @param str
     * @param clz
     * @return
     * @param <T>
     */
    public static <T> List<T> toList(String str, Class<T> clz) {
        try {
            return jsonMapper.readValue(str, new TypeReference<List<T>>() {
            }); // 读取JSON并转换为列表
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> List<T> toList(String str, TypeReference<List<T>> typeReference) {
        try {
            // 使用传入的TypeReference读取JSON并转换为列表
            return jsonMapper.readValue(str, typeReference);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 将JSON字符串转换为JsonNode对象
     * @param str
     * @return
     */
    public static JsonNode toJsonNode(String str) {
        try {
            return jsonMapper.readTree(str);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 将JsonNode对象转换为指定类型的值
     * @param node
     * @param clz
     * @return
     * @param <T>
     */
    public static <T> T nodeToValue(JsonNode node, Class<T> clz) {
        try {
            return jsonMapper.treeToValue(node, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 将Java对象转换为JSON字符串
     * @param t
     * @return
     */
    public static String toStr(Object t) {
        try {
            return jsonMapper.writeValueAsString(t);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }
}

