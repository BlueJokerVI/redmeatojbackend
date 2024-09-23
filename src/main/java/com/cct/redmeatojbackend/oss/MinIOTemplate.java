package com.cct.redmeatojbackend.oss;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.cct.redmeatojbackend.common.domain.enums.RespCodeEnum;
import com.cct.redmeatojbackend.common.utils.ThrowUtils;
import com.cct.redmeatojbackend.oss.domain.dto.OssRequest;
import com.cct.redmeatojbackend.oss.domain.vo.OssResponse;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class MinIOTemplate {

    /**
     * MinIO 客户端
     */
    MinioClient minioClient;

    /**
     * MinIO 配置类
     */
    OssProperties ossProperties;

    /**
     * 查询所有存储桶
     *
     * @return Bucket 集合
     */
    @SneakyThrows(Exception.class)
    public List<Bucket> listBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 桶是否存在
     *
     * @param bucketName 桶名
     * @return 是否存在
     */
    @SneakyThrows(Exception.class)
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 桶名
     */
    @SneakyThrows(Exception.class)
    public void makeBucket(String bucketName) {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 删除一个空桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param bucketName 桶名
     */
    @SneakyThrows(Exception.class)
    public void removeBucket(String bucketName) {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除一个目录。
     *
     * @param bucketName 桶名
     */
    @SneakyThrows(Exception.class)
    public void removeDir(String bucketName, String directoryPrefix) {
        // 列出目录下的所有对象并删除
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .prefix(directoryPrefix)
                .recursive(true)
                .build());

        for (Result<Item> result : results) {
            Item item = result.get();
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(item.objectName())
                    .build());
        }
    }


    /**
     * 返回临时带签名、过期时间一天、PUT请求方式的访问URL
     */
    @SneakyThrows(Exception.class)
    public OssResponse getPreSignedObjectUrl(OssRequest req) {
        String absolutePath = req.isAutoPath() ? generateAutoPath(req) : req.getFilePath() + StrUtil.SLASH + req.getFileName();
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(ossProperties.getBucketName())
                        .object(absolutePath)
                        .expiry(60 * 60 * 24)
                        .build());
        return OssResponse.builder()
                .uploadUrl(url)
                .downloadUrl(getDownloadUrl(ossProperties.getBucketName(), absolutePath))
                .build();
    }

    private String getDownloadUrl(String bucket, String pathFile) {
        return ossProperties.getEndpoint() + StrUtil.SLASH + bucket + StrUtil.SLASH + pathFile;
    }

    /**
     * GetObject接口用于获取某个文件（Object）。此操作需要对此Object具有读权限。
     *
     * @param bucketName  桶名
     * @param ossFilePath Oss文件路径
     */
    @SneakyThrows(Exception.class)
    public InputStream getObject(String bucketName, String ossFilePath) {
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucketName).object(ossFilePath).build());
    }


    /**
     * 普通文件上传
     *
     * @param bucketName  桶名
     * @param ossFilePath Oss文件路径
     * @param sourceFile  本地文件
     */
    @SneakyThrows(Exception.class)
    public void putObjectWithFile(String bucketName, String ossFilePath, File sourceFile) {
        // 上传文件
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        //oss文件路径
                        .object(ossFilePath)
                        // 文件输入流
                        .stream(Files.newInputStream(Paths.get(sourceFile.getPath())), sourceFile.length(), -1)
                        // 文件类型
                        .contentType("text/plain")
                        .build()
        );
    }


    /**
     * 将String内容作为上传文件的内容
     *
     * @param bucketName
     * @param ossFilePath
     * @param context
     */
    @SneakyThrows(Exception.class)
    public void putObjectWithString(String bucketName, String ossFilePath, String context) {

        ThrowUtils.throwIf(StrUtil.isEmpty(context), RespCodeEnum.PARAMS_ERROR, "上传内容不能为空");

        byte[] bytes = context.getBytes(StandardCharsets.UTF_8);
        int byteLen = bytes.length;

        // 上传文件
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        //oss文件路径
                        .object(ossFilePath)
                        // 文件输入流
                        .stream(new ByteArrayInputStream(context.getBytes(StandardCharsets.UTF_8)), byteLen, -1)
                        // 文件类型
                        .contentType("text/plain")
                        .build()
        );
    }


    /**
     * 获取minio存储的某个存储桶下，某个路径下的所有文件对象元信息
     *
     * @param bucketName
     * @param prefixPath
     * @param recursive
     * @return
     */
    @SneakyThrows(Exception.class)
    public Iterable<Result<Item>> listObjects(String bucketName, String prefixPath, boolean recursive) {
        return minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).prefix(prefixPath).recursive(recursive).build());
    }

    /**
     * 生成随机文件名，防止重复
     *
     * @return
     */
    public String generateAutoPath(OssRequest req) {
        cn.hutool.core.lang.UUID uuid = cn.hutool.core.lang.UUID.fastUUID();
        String suffix = FileNameUtil.getSuffix(req.getFileName());
        String yearAndMonth = DateUtil.format(new Date(), DatePattern.NORM_MONTH_PATTERN);
        return req.getFilePath() + StrUtil.SLASH + yearAndMonth + StrUtil.SLASH + uuid + StrUtil.DOT + suffix;
    }

    /**
     * 获取带签名的临时上传元数据对象，前端可获取后，直接上传到Minio
     * <p>
     * 用于生成预签名的表单数据，用于将文件上传到一个基于 MinIO 的存储桶中。
     * 预签名表单数据允许客户端上传文件到存储桶，而无需直接暴露存储桶的访问凭证。
     *
     * @param bucketName
     * @param fileName
     * @return
     */
    @SneakyThrows(Exception.class)
    public Map<String, String> getPreSignedPostFormData(String bucketName, String fileName) {
        // 为存储桶创建一个上传策略，过期时间为7天
        PostPolicy policy = new PostPolicy(bucketName, ZonedDateTime.now().plusDays(7));
        // 设置一个参数key，值为上传对象的名称
        policy.addEqualsCondition("key", fileName);
        // 添加Content-Type以"image/"开头，表示只能上传照片
        policy.addStartsWithCondition("Content-Type", "image/");
        // 设置上传文件的大小 64kiB to 10MiB.
        policy.addContentLengthRangeCondition(64 * 1024, 10 * 1024 * 1024);
        return minioClient.getPresignedPostFormData(policy);
    }

}