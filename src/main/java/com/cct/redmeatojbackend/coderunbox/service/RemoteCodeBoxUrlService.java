package com.cct.redmeatojbackend.coderunbox.service;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 代码沙箱远程地址服务
 */

@Service
public class RemoteCodeBoxUrlService {

    /**
     * hash环
     */
    private final SortedMap<Integer, String> hashRing = new TreeMap<>();


    @Value("${codebox.urls}")
    private String[] urls;

    @PostConstruct
    public void init() {
        // 初始化一致性哈希环，加入虚拟节点
        for (String url : urls) {
            //默认100个虚拟节点，可以通过配置文件修改
            int virtualNodes = 100;
            for (int i = 0; i < virtualNodes; i++) {
                String virtualNode = url + "&&VN" + i;
                int hash = getHash(virtualNode);
                hashRing.put(hash, url);
            }
        }
    }

    /**
     * 计算哈希值 ,对key进行md5加密，然后取其低32位作为哈希值
     * @param key
     * @return
     */
    private int getHash(String key) {
        byte[] bytes = DigestUtil.md5(key);
        return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }

    /**
     * 根据输入数据找到最近的服务器节点
     * @param key
     * @return
     */
    public String getServerUrl(String key) {
        int hash = getHash(key);
        // 顺时针找到第一个大于等于该哈希值的节点
        SortedMap<Integer, String> tailMap = hashRing.tailMap(hash);
        if (tailMap.isEmpty()) {
            // 如果哈希值超出范围，返回第一个节点
            return hashRing.get(hashRing.firstKey());
        }
        return tailMap.get(tailMap.firstKey());
    }
}
