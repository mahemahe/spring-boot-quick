package quick.others;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * java8 future工具类
 * @author wangxiaolong <xiaolong@maihaoche.com>
 * @date 17/12/7 下午1:23
 */
public class FutureUtils {

    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

    /**
     * 获取执行结果，如果有异常则打印日志，消费异常，返回结果为null
     * @param completableFuture
     * @param logConsumer
     * @param <T>
     * @return
     */
    public static <T> T getResultWithDrownException(CompletableFuture<T> completableFuture, Consumer<Exception>
            logConsumer){
        try {
            return completableFuture
                    .exceptionally(buildThrowableExceptionFunction(logConsumer))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            logConsumer.accept(e);
            return null;
        }
    }

    private static <T> Function<Throwable, T> buildThrowableExceptionFunction(Consumer<Exception> logConsumer) {
        return e -> {
            logConsumer.accept((Exception) e);
            return null;
        };
    }

    /**
     * 获取执行结果，如果有异常则打印日志，抛出运行时异常
     * @param completableFuture
     * @param logConsumer
     * @param <T>
     * @return
     */
    public static <T> T getResultWithThrowException(CompletableFuture<T> completableFuture, Consumer<Exception> logConsumer){
        try {
            return completableFuture
                    .exceptionally(buildThrowableExceptionFunction(logConsumer))
                    .get();
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logConsumer.accept(e);
            throw new RuntimeException(e);
        }
    }
}
