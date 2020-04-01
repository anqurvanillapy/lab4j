class Tracer implements AutoCloseable {
    private final String funcName;

    public Tracer() {
        this.funcName = Thread.currentThread().getStackTrace()[2].toString();
        System.out.println("Enter: " + this.funcName);
    }

    @Override
    public void close() {
        System.out.println("Exit: " + this.funcName);
    }
}

public class TracerMain {
    public static void main(String[] args) {
        try (Tracer ignored = new Tracer()) {
            System.out.println(42);
        }
    }
}
