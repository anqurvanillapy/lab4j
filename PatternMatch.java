/**
 *  Check out this post: https://zhuanlan.zhihu.com/p/46638719.
 */

public class PatternMatch {
    public static void main(String[] args) {
        Option<Integer> foo = Option.some(42);

        foo.match(new OptionVisitor<> () {
            public Integer ofSome(Integer value) {
                System.out.println(value);
                return value;
            }

            public Integer ofNone() {
                System.out.println("None");
                return null;
            }
        });
    }
}

interface OptionVisitor<T, U> {
    U ofSome(T value);
    U ofNone();
}

abstract class Option<T> {
    public abstract <U> U match(OptionVisitor<? super T, ? extends U> value);

    public static <T> Option<T> some(T value) {
        if (value == null) {
            throw new NullPointerException();
        } else {
            return new Some<T>(value);
        }
    }

    public static <T> Option<T> none() {
        return new None<T>();
    }

    public T orElse(T other) {
        return this.match(new OptionVisitor<>() {
            public T ofSome(T value) { return value; }
            public T ofNone() { return other; }
        });
    }
}

final class Some<T> extends Option<T> {
    private final T value;

    public Some(T value) {
        this.value = value;
    }

    @Override
    public <U> U match(OptionVisitor<? super T, ? extends U> value) {
        return value.ofSome(this.value);
    }
}


final class None<T> extends Option<T> {
    @Override
    public <U> U match(OptionVisitor<? super T, ? extends U> value) {
        return value.ofNone();
    }
}
