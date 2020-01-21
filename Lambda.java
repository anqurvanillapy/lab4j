interface Strategy {
    public int Func(int n);
}

public class Lambda {
    public static void main(String[] args) {
        // Anonymous class.
        Strategy a = new Strategy() {
            @Override
            public int Func(int n) {
                return n * n;
            }
        };

        // Lambda expression.
        Strategy b = x -> x * x;

        System.out.println(a.Func(2));
        System.out.println(b.Func(2));
    }
}
