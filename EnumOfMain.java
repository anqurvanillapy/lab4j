import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

interface EnumOf<T> {
    T getValue();

    @Nullable
    static <E extends Enum<E> & EnumOf<T>, T> E enumOf(@NotNull Class<E> e, T v) {
        if (v == null) {
            return null;
        }
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
        if (k == null) {
            return null;
        }
        for (E kind : e.getEnumConstants()) {
            if (kind.getKey() == k) {
                return kind.getValue();
            }
        }
        return null;
    }

    @Nullable
    static <E extends Enum<E> & KvEnumOf<T, U>, T, U>
    E enumOf(@NotNull Class<E> e, T k, U v) {
        if (k == null || v == null) {
            return null;
        }
        for (E kind : e.getEnumConstants()) {
            if (kind.getKey() == k && kind.getValue() == v) {
                return kind;
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
        String s0 = "A";
        Kind k0 = EnumOf.enumOf(Kind.class, s0);
        System.out.println(k0);

        Integer k1 = 0;
        String v1 = "A";
        KvKind kv1 = KvEnumOf.enumOf(KvKind.class, k1, v1);
        System.out.println(kv1);

        Integer k2 = 0;
        String v2 = KvEnumOf.valueOf(KvKind.class, k2);
        System.out.println(v2);
    }
}
