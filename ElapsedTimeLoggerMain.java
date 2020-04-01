class ElapsedTimeLogger implements AutoCloseable {
    private final long startTime = System.currentTimeMillis();

    @Override
    public void close() {
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println(String.format("Elapsed: %d ms", (int) elapsed));
    }
}

public class ElapsedTimeLoggerMain {
    public static void main(String[] args) {
        int n = 42;
        try (ElapsedTimeLogger ignored = new ElapsedTimeLogger()) {
            System.out.println(n);
        }
        System.out.println(n);
    }
}
