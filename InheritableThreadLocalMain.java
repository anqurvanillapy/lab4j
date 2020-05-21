import java.util.stream.IntStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;

public class InheritableThreadLocalMain {
    private static ThreadLocal<Integer> tl = new InheritableThreadLocal<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        tl.set(42);

        IntStream
            .range(0, 8)
            .boxed()
            .map(n -> pool.submit(() -> tl.get()))
            .map(fut -> {
                try {
                    return fut.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            })
            .forEach(n -> System.out.println(n));

        pool.shutdownNow();
    }
}
