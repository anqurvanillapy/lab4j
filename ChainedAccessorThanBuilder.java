import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@Accessors(chain = true)
class Foo {
    private Date date; // passing reference is dangerous here
    private String hello;

    public Date getDate() {
        return (Date) date.clone();
    }

    public Foo setDate(@NotNull Date date) {
        this.date = (Date) date.clone();
        return this; // make it chained
    }
}

public class ChainedAccessorThanBuilder {
    public static void main(String[] args) {
        Foo foo = new Foo().setDate(new Date());
        Date a = foo.getDate();
        Date b = foo.getDate();
        System.out.println(a == b); // false

        Date c = new Date();
        foo.setDate(c);
        System.out.println(c == foo.getDate()); // false

        Date d = new Date();
        Foo bar = new Foo().setDate(d);
        System.out.println(d == bar.getDate()); // false

        // Builder-like usage.
        Foo baz = new Foo()
            .setDate(new Date())
            .setHello("hello");
        System.out.println(baz);
    }
}
