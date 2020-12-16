package quick.others;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

/**
 * 常用线程池
 *
 * @author wangxiaolong <xiaolong@maihaoche.com>
 * @date 18/5/3 下午7:58
 */
public class ExecutorConstant {
    /**
     * 公共线程池
     * 一般用于内存内计算任务
     */
    public static final Executor COMMON_EXECUTOR = Executors.newCachedThreadPool();
    /**
     * 访问外网IO线程池
     */
    public static final Executor OUTER_NET_IO_EXECUTOR = new ThreadPoolExecutor(
            2, 2,
            1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(2048),
            new BasicThreadFactory.Builder().
                    namingPattern("outer-net-io-pool-%d")
                    .build());

    /**
     * 访问外网IO线程池
     */
    public static final Executor OUTER_NET_SINGLE_IO_EXECUTOR = new ThreadPoolExecutor(
            1, 1,
            1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(2048),
            new BasicThreadFactory.Builder().
                    namingPattern("outer-net-io-pool-%d")
                    .build());

    /**
     * 访问内网IO线程池
     */
    public static final Executor INNER_NET_IO_EXECUTOR = new ThreadPoolExecutor(
            24, 24,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new BasicThreadFactory.Builder().
                    namingPattern("inner-net-io-pool-%d")
                    .build());

    /**
     * 不担心处理后结果的线程池
     */
    public static final Executor NOT_CARE_RESULT_EXECUTOR = new ThreadPoolExecutor(
            2, 8,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10000),
            new BasicThreadFactory.Builder().
                    namingPattern("not-care-result-pool-%d")
                    .build());

    /**
     * 批处理线程池
     */
    public static final Executor BATCH_HANDLE_EXECUTOR = new ThreadPoolExecutor(
            4, 4,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new BasicThreadFactory.Builder().
                    namingPattern("batch-handle-pool-%d")
                    .build());
    /**
     * EventBus线程池
     */
    public static final ExecutorService EVENT_BUS_EXECUTOR = new ThreadPoolExecutor(
            8, 16,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new BasicThreadFactory.Builder().
                    namingPattern("event-bus-pool-%d")
                    .daemon(true)
                    .build());

    /**
     * 用于数据分析线程池
     */
    public static final Executor ANALYZE_HANDLE_EXECUTOR = new ThreadPoolExecutor(
            4, 4,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new BasicThreadFactory.Builder().
                    namingPattern("analyze-handle-pool-%d")
                    .build());

    /**
     * 用于日志线程池
     */
    public static final Executor LOG_EXECUTOR = new ThreadPoolExecutor(
            4, 4,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new BasicThreadFactory.Builder().
                    namingPattern("log-pool-%d")
                    .daemon(true)
                    .build());

    /**
     * 用于询价定时任务
     */
    public static final ScheduledExecutorService NOTICE_QUOT_SCHEDULER = new ScheduledThreadPoolExecutor(
            1,
            new BasicThreadFactory.Builder().
                    namingPattern("notice-quot-schedule-pool-%d")
                    .build()
    );

    /**
     * 用于直播定时任务
     */
    public static final ScheduledExecutorService VIDEO_LIVE_SCHEDULER = new ScheduledThreadPoolExecutor(
            1,
            new BasicThreadFactory.Builder().
                    namingPattern("video_live-schedule-pool-%d")
                    .build()
    );

    /**
     * 用于spring定时任务
     */
    public static final ScheduledExecutorService SPRING_SCHEDULER = new ScheduledThreadPoolExecutor(
            10,
            new BasicThreadFactory.Builder().
                    namingPattern("spring-schedule-pool-%d")
                    .build()
    );


    private ExecutorConstant() {
    }
}
