package com.cct.redmeatojbackend.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 帖子热度分计算工具类
 *
 * <p>本类提供基于帖子互动数据（点赞、收藏、评论、浏览）和时间衰减的热度分计算功能，
 * 确保结果值为非负整数且不超过 {@link Integer#MAX_VALUE}。
 *
 * <p><b>线程安全：</b> 本工具类无状态，所有方法均为静态方法，线程安全。
 *
 * @author cct
 */
public class HotScoreUtil {
    /**
     * 权重配置
     */
    private static final int THUMBS_UP_WEIGHT = 10;
    private static final int COLLECT_WEIGHT = 5;
    private static final int COMMENT_WEIGHT = 3;
    private static final int VIEW_WEIGHT = 1;
    private static final int MAX_HOT_SCORE = Integer.MAX_VALUE;

    /**
     * 计算热度分（基础版）
     *
     * @param thumbsUp   点赞数（需 >=0）
     * @param collect    收藏数（需 >=0）
     * @param comment    评论数（需 >=0）
     * @param view       浏览数（需 >=0）
     * @param createTime 内容创建时间（不可为 null）
     * @return 非负热度分 [0, Integer.MAX_VALUE]
     * @throws IllegalArgumentException 如果互动数为负数或时间为空
     */
    public static int calculateHotScore(
            int thumbsUp,
            int collect,
            int comment,
            int view,
            Date createTime) {
        validateInput(thumbsUp, collect, comment, view, createTime);
        long hours = getHoursSinceCreation(createTime);
        return computeScore(thumbsUp, collect, comment, view, hours);
    }

    /**
     * 计算热度分（时间戳版）
     *
     * @param thumbsUp         点赞数（需 >=0）
     * @param collect          收藏数（需 >=0）
     * @param comment          评论数（需 >=0）
     * @param view             浏览数（需 >=0）
     * @param createTimeMillis 创建时间戳（毫秒，需 >0）
     */
    public static int calculateHotScore(
            int thumbsUp,
            int collect,
            int comment,
            int view,
            long createTimeMillis) {
        validateInput(thumbsUp, collect, comment, view, createTimeMillis);
        long hours = getHoursSinceCreation(createTimeMillis);
        return computeScore(thumbsUp, collect, comment, view, hours);
    }

    //------------------------ 核心算法 ------------------------
    private static int computeScore(int thumbsUp, int collect, int comment, int view, long hours) {
        double timeDecay = Math.max(Math.log(hours + 2), 1.0);
        long interactionScore =
                (long) THUMBS_UP_WEIGHT * thumbsUp
                        + (long) COLLECT_WEIGHT * collect
                        + (long) COMMENT_WEIGHT * comment
                        + (long) VIEW_WEIGHT * view;
        return clampToValidRange(interactionScore / timeDecay);
    }

    //------------------------ 工具方法 ------------------------
    private static void validateInput(int thumbsUp, int collect, int comment, int view, Date createTime) {
        if (createTime == null) {
            throw new IllegalArgumentException("createTime cannot be null");
        }
        validateCounts(thumbsUp, collect, comment, view);
    }

    private static void validateInput(int thumbsUp, int collect, int comment, int view, long createTimeMillis) {
        if (createTimeMillis <= 0) {
            throw new IllegalArgumentException("createTimeMillis must be positive");
        }
        validateCounts(thumbsUp, collect, comment, view);
    }

    private static void validateCounts(int thumbsUp, int collect, int comment, int view) {
        if (thumbsUp < 0 || collect < 0 || comment < 0 || view < 0) {
            throw new IllegalArgumentException("Interaction counts cannot be negative");
        }
    }

    private static long getHoursSinceCreation(Date createTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime postTime = convertToLocalDateTime(createTime);
        return Duration.between(postTime, now).toHours();
    }

    private static long getHoursSinceCreation(long createTimeMillis) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime postTime = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(createTimeMillis),
                java.time.ZoneId.systemDefault()
        );
        return Duration.between(postTime, now).toHours();
    }

    private static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }

    private static int clampToValidRange(double value) {
        if (Double.isNaN(value)) return 0;
        double rounded = Math.round(value);
        return (int) Math.min(Math.max(rounded, 0), MAX_HOT_SCORE);
    }
}