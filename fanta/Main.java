public class Main {
    public static void main(String[] args) {
        System.out.println("StringSetoid");
        System.out.println("'123'=='123': " +
            (new StringSetoid())._$_equals("123", "123"));

        System.out.println("NumberSetoid");
        System.out.println("123==123: " +
            (new NumberSetoid())._$_equals(123, 123));
    }
}
