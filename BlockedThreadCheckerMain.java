import java.lang.Thread.State;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class BlockedThreadCheckerMain {
    public static void main(String[] args) throws InterruptedException {
        try (final BlockedThreadChecker checker = new BlockedThreadChecker(500, TimeUnit.MILLISECONDS)) {
            final Thread t1 = new Thread(() -> job(500));
            final Thread t2 = new Thread(() -> job(1500));

            checker.registerThread(t1, new Task());
            checker.registerThread(t2, new Task());

            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }
    }

    private static void job(long milli) {
        try {
            Thread.sleep(milli);
            System.out.println(Thread.currentThread() + ": Done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Task implements BlockedThreadChecker.Task {
        private final long startTime = System.nanoTime();

        @Override
        public long startTimeNano() {
            return startTime;
        }
    }
}

/**
 * See Vert.x's BlockedThreadChecker.
 */
final class BlockedThreadChecker implements AutoCloseable {
    public interface Task {
        long startTimeNano();

        default long timeLimit() {
            return 1000;
        }

        default TimeUnit timeLimitUnit() {
            return TimeUnit.MILLISECONDS;
        }
    }

    private final Map<Thread, Task> tasks = new WeakHashMap<>();
    private final Timer timer;

    public BlockedThreadChecker(long period, TimeUnit periodUnit) {
        final long periodMilli = periodUnit.toMillis(period);
        timer = new Timer("blocked-thread-checker", true);
        timer.schedule(TimerUtil.newTimerTask(this::timerTask), periodMilli, periodMilli);
    }

    private void timerTask() {
        synchronized (BlockedThreadChecker.this) {
            final long now = System.nanoTime();

            for (Map.Entry<Thread, Task> entry : tasks.entrySet()) {
                final Thread thread = entry.getKey();
                final Task task = entry.getValue();

                final long startTime = task.startTimeNano();
                final long timeLimit = task.timeLimit();
                final TimeUnit timeLimitUnit = task.timeLimitUnit();
                final long dur = timeLimitUnit.convert(now - startTime, TimeUnit.NANOSECONDS);

                if (startTime == 0 || dur < timeLimit || thread.getState() == State.TERMINATED) {
                    continue;
                }

                System.err.print(thread + " blocked for " + dur + "ms, time limit is "
                        + TimeUnit.MILLISECONDS.convert(timeLimit, timeLimitUnit) + "ms: ");
                Exception ex = new RuntimeException("Thread blocked");
                ex.setStackTrace(thread.getStackTrace());
                ex.printStackTrace();
            }
        }
    }

    public synchronized void registerThread(Thread thread, Task task) {
        tasks.put(thread, task);
    }

    @Override
    public void close() {
        timer.cancel();
    }
}

final class TimerUtil {
    public static TimerTask newTimerTask(Runnable r) {
        return new TimerTask() {
            @Override
            public void run() {
                r.run();
            }
        };
    }
}
