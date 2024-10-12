package com.cct.redmeatojbackend.common.thread;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: redmeatojbackend
 * @Author: cct
 * @Description: 线程池配置类
 */

@Configuration
@EnableAsync
public class ThreadPoolConfig {


    /**
     * 运行代码模块线程池
     */
    public static final String CODE_BOX_EXECUTOR = "codeBoxExecutor";
    /**
     *  内存监控线程池
     */
    public static final String CODE_BOX_MONITOR_MEMORY_EXECUTOR = "codeBoxMonitorMemoryExecutor";
    
    @Bean(name = CODE_BOX_EXECUTOR)
    public Executor codeBoxInputExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //实现优雅停机，使线程池内任务执行完毕在停机
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("codeRunBox-executor-");
        //装饰器模式替换增强后的MyThreadFactory
        executor.setThreadFactory(new MyThreadFactory(executor));
        //满了调用线程执行，认为重要任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }


    @Bean(name = CODE_BOX_MONITOR_MEMORY_EXECUTOR)
    public ScheduledThreadPoolExecutor codeBoxMonitorMemoryExecutor() {
        return new ScheduledThreadPoolExecutor(10, new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("MemoryMonitorThread-" + threadNumber.getAndIncrement());
                thread.setDaemon(true);  // 设置为守护线程
                return thread;
            }
        });
    }


}
