import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NaturalOrderMain {
    public static void main(String[] args) {
        List<String> nums = Arrays.asList("1", "10", "12", "20", "24", "30");

        System.out.println(nums.stream()
            .map(Integer::parseInt)
            .filter(Objects::nonNull)
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.toList()));
    }
}
