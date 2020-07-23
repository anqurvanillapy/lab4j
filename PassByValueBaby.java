public class PassByValueBaby {
    private static class Foo {
        private int data;
        int getData() { return data; }
        void setData(int data) { this.data = data; }
    }

    private static class Bar {
        private Integer data;
        Integer getData() { return data; }
        void setData(Integer data) { this.data = data; }
    }

    private static void useFoo(Foo foo) {
        foo = new Foo();
        foo.setData(42);
    }

    private static void useBar(Bar bar) {
        bar = new Bar();
        bar.setData(42);
    }

    public static void main(String[] args) {
        System.out.println("Pass-by-value baby!");
        Foo foo = new Foo();
        useFoo(foo);
        System.out.println(foo.getData());
        Bar bar = new Bar();
        useBar(bar);
        System.out.println(bar.getData());
    }
}
