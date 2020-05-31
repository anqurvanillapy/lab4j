import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

public class GroupTaskMain {
    public static void main(String[] args) {
        final int itemSize = 1000;
        final int groupSize = 50;
        final Random rand = new Random();
        final ExecutorService pool = Executors.newFixedThreadPool(4);

        List<Integer> inputItems = IntStream
            .range(0, itemSize)
            .map(n -> rand.nextInt(100))
            .boxed()
            .collect(Collectors.toList());

        Integer result = IntStream
            .range(0, itemSize / groupSize)
            .map(n -> n * groupSize)
            .boxed()
            .map(n -> inputItems
                .stream()
                .skip(n)
                .limit(groupSize)
                .collect(Collectors.toList()))
            .map(nums ->
                pool.submit(() -> nums.stream().reduce(0, Math::addExact)))
            .map(fut -> {
                try {
                    return fut.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    return 0;
                }
            })
            .reduce(0, Math::addExact);

        System.out.println("With group task=" + result);
        System.out.println("Without group task=" +
            inputItems.stream().reduce(0, Math::addExact));
        pool.shutdown();
    }
}
