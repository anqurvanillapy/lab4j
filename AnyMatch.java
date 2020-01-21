import java.util.Arrays;

public class AnyMatch {
    private static boolean containsNumber(int[] arr, int a) {
        return Arrays.stream(arr).anyMatch(n -> n == a);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        boolean found = containsNumber(arr, 3);
        System.out.println(found);
    }
}
