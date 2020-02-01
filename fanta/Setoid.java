public interface Setoid<T> {
    public boolean _$_equals(T lhs, T rhs);
}

class StringSetoid implements Setoid<String> {
    public boolean _$_equals(String lhs, String rhs) {
        return lhs.equals(rhs);
    }
}

class NumberSetoid implements Setoid<Number> {
    public boolean _$_equals(Number lhs, Number rhs) {
        return lhs == rhs;
    }
}
