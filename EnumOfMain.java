import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

interface EnumOf<T> {
    T getValue();

    @Nullable
    static <E extends Enum<E> & EnumOf<T>, T> E enumOf(@NotNull Class<E> e, T v) {
        for (E kind : e.getEnumConstants()) {
            if (kind.getValue() == v) {
                return kind;
            }
        }
        return null;
    }
}

interface KvEnumOf<T, U> {
    T getKey();

    U getValue();

    @Nullable
    static <E extends Enum<E> & KvEnumOf<T, U>, T, U>
    U valueOf(@NotNull Class<E> e, T k) {
        for (E kind : e.getEnumConstants()) {
            if (kind.getKey() == k) {
                return kind.getValue();
            }
        }
        return null;
    }
}

@AllArgsConstructor
enum Kind implements EnumOf<String> {
    A("A"),
    B("B");

    @Getter
    private final String value;
}

@AllArgsConstructor
@Getter
enum KvKind implements KvEnumOf<Integer, String> {
    A(0, "A"),
    B(1, "B");

    private final Integer key;
    private final String value;
}

public class EnumOfMain {
    public static void main(String[] args) {
        String s = "A";
        Kind k = EnumOf.enumOf(Kind.class, s);
        System.out.println(k);

        Integer n = 0;
        String v = KvEnumOf.valueOf(KvKind.class, n);
        System.out.println(v);
    }
}
