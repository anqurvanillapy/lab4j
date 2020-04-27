import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

interface UnitEnum<T> {
    T getValue();

    @Nullable
    static <E extends Enum<E> & UnitEnum<T>, T> E enumOf(@NotNull Class<E> e, T v) {
        if (v == null) {
            return null;
        }
        for (E kind : e.getEnumConstants()) {
            if (kind.getValue().equals(v)) {
                return kind;
            }
        }
        return null;
    }
}

interface PairEnum<T, U> {
    T getKey();

    U getValue();

    @Nullable
    static <E extends Enum<E> & PairEnum<T, U>, T, U>
    U valueOf(@NotNull Class<E> e, T k) {
        if (k == null) {
            return null;
        }
        for (E kind : e.getEnumConstants()) {
            if (kind.getKey().equals(k)) {
                return kind.getValue();
            }
        }
        return null;
    }

    @Nullable
    static <E extends Enum<E> & PairEnum<T, U>, T, U>
    E enumOf(@NotNull Class<E> e, T k, U v) {
        if (k == null || v == null) {
            return null;
        }
        for (E kind : e.getEnumConstants()) {
            if (kind.getKey().equals(k) && kind.getValue().equals(v)) {
                return kind;
            }
        }
        return null;
    }
}

@AllArgsConstructor
enum UnitKind implements UnitEnum<String> {
    A("A"),
    B("B");

    @Getter
    private final String value;
}

@AllArgsConstructor
@Getter
enum PairKind implements PairEnum<Integer, String> {
    A(0, "A"),
    B(1, "B");

    private final Integer key;
    private final String value;
}

public class AdHocEnum {
    public static void main(String[] args) {
        String s0 = "A";
        UnitKind k0 = UnitEnum.enumOf(UnitKind.class, s0);
        System.out.println(k0);

        Integer k1 = 0;
        String v1 = "A";
        PairKind kv1 = PairEnum.enumOf(PairKind.class, k1, v1);
        System.out.println(kv1);

        Integer k2 = 0;
        String v2 = PairEnum.valueOf(PairKind.class, k2);
        System.out.println(v2);
    }
}
