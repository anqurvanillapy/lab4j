public class ClassLoaderModel {
    public static void main(String[] args) {
        System.out.println(App.class.getClassLoader());
        System.out.println(DerivedApp.class.getClassLoader());

        System.out.println(App.class.getClassLoader().getParent());
        System.out.println(DerivedApp.class.getClassLoader().getParent());

        System.out.println(App.class
            .getClassLoader()
            .getParent().getParent());
        System.out.println(DerivedApp.class
            .getClassLoader()
            .getParent().getParent()); // null: Boostrap class loader
    }
}

class App {
}

class DerivedApp extends App {
}
