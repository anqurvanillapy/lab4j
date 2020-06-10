import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

class Student {
    private final Integer id;
    private final Integer score;

    public Student(Integer id, Integer score) {
        this.id = id;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public String toString() {
        return "Student(id=" + id + ", score=" + score + ")";
    }
}

public class StudentRank {
    public static void main(String[] args) {
        Random rand = new Random();

        Map<Integer, List<Student>> students = IntStream
            .range(0, 5000)
            .boxed()
            .map(n -> new Student(n, rand.nextInt(751)))
            .collect(Collectors.groupingBy(Student::getScore));

        Map<Integer, Integer> studentNums = students
            .entrySet()
            .stream()
            .filter(p -> p.getKey() > 730)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                x -> x.getValue().size(),
                Math::addExact,
                TreeMap::new));
        System.out.println(studentNums);

        Student me = new Student(4242, 742);
        System.out.println(me);

        Integer myRank = students
            .entrySet()
            .stream()
            .filter(p -> p.getKey() > me.getScore())
            .map(p -> p.getValue().size())
            .reduce(1, (total, n) -> total + n);
        System.out.println("My rank: " + myRank);
    }
}
