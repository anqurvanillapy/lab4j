import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Foo {
}

class Base {
}

@Foo
class Derived extends Base {
}

public class ChildRuntimeAnnotationMain {
    public static void main(String[] args) {
        Base x = new Derived();
        System.out.println(Arrays.asList(x.getClass().getDeclaredAnnotations()));
    }
}
