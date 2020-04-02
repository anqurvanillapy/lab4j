import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

interface Sayable {
    void say();
}

abstract class InfoSayable implements Sayable {
    private String info;
    public InfoSayable(String info) { this.info = info; }
    public String getInfo() { return info; }
}

class FooInfoSayer extends InfoSayable {
    public FooInfoSayer(String info) { super(info); }
    @Override public void say() { System.out.println("Foo: " + getInfo()); }
}

class BarInfoSayer extends InfoSayable {
    public BarInfoSayer(String info) { super(info); }
    @Override public void say() { System.out.println("Bar: " + getInfo()); }
}

class InfoSayableFactory {
    private Constructor<? extends InfoSayable> ctor = null;

    public InfoSayableFactory(Class<? extends InfoSayable> clazz) {
        try {
            this.ctor = clazz.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    InfoSayable create(String info) {
        if (ctor == null) {
            return null;
        }

        try {
            return ctor.newInstance(info);
        } catch (IllegalAccessException
                | InstantiationException
                | InvocationTargetException e) {
            return null;
        }
    }
}

public class ClassObjectFactoryMain {
    public static void main(String[] args) {
        InfoSayableFactory fooFactory = new InfoSayableFactory(FooInfoSayer.class);
        InfoSayable foo = fooFactory.create("hello");
        foo.say();

        InfoSayableFactory barFactory = new InfoSayableFactory(BarInfoSayer.class);
        InfoSayable bar = barFactory.create("hello");
        bar.say();
    }
}
