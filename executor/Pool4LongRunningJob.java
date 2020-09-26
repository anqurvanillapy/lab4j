import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool4LongRunningJob {
    private static final int THREAD_NUM = 4;
    private static final int TASK_NUM = 50;

    // It depends... shit
    private static final ExecutorService good = Executors.newWorkStealingPool(THREAD_NUM);
    private static final ExecutorService bad0 = Executors.newScheduledThreadPool(THREAD_NUM);
    private static final ExecutorService bad1 = Executors.newFixedThreadPool(THREAD_NUM);

    private static CountDownLatch latch;

    private static void task() {
        try {
            Thread.sleep(latch.getCount() % 2 == 0 ? 10 : 1000);
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        latch = new CountDownLatch(TASK_NUM);
        long start = System.currentTimeMillis();
        for (int i = 0; i < TASK_NUM; i++) good.submit(Pool4LongRunningJob::task);
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
        good.shutdown();

        latch = new CountDownLatch(TASK_NUM);
        start = System.currentTimeMillis();
        for (int i = 0; i < TASK_NUM; i++) bad0.submit(Pool4LongRunningJob::task);
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
        bad0.shutdown();

        latch = new CountDownLatch(TASK_NUM);
        start = System.currentTimeMillis();
        for (int i = 0; i < TASK_NUM; i++) bad1.submit(Pool4LongRunningJob::task);
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
        bad1.shutdown();
    }
}
