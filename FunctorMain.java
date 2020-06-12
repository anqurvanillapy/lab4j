import java.util.function.Function;
import java.util.Optional;
import java.util.Optional;
import java.util.Objects;
import java.util.Map;
import java.util.AbstractMap;
import java.util.stream.Stream;
import java.util.stream.Collectors;

interface Functor <T> {
    <R> Functor<R> map(Function<T, R> f);
}

class FreeFunctor <T> implements Functor<T> {
    private final T value;

    private FreeFunctor(T value) {
        this.value = value;
    }

    public static <R> Functor<R> pure(R value) {
        return new FreeFunctor<>(value);
    }

    @Override
    public <R> Functor<R> map(Function<T, R> f) {
        return FreeFunctor.pure(f.apply(value));
    }
}

public class FunctorMain {
    public static void main(String[] args) {
        final Map<String, Integer> map = Stream.of(
            new AbstractMap.SimpleEntry<>("foo", 42))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Why bother??
        FreeFunctor.pure(map.get("foo"))
            .map(v -> {
                if (Objects.isNull(v)) {
                    System.out.println("Key not found");
                }
                return v;
            })
            .map(v -> {
                Optional.ofNullable(v).ifPresent(System.out::println);
                return v;
            });

        // Ooo y!!
        Optional.ofNullable(map.get("bar"))
            .map(v -> {
                System.out.println(v);
                return v;
            })
            .orElseGet(() -> {
                System.out.println("Key not found");
                return null;
            });
    }
}
