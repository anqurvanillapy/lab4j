import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.List;
import java.io.Serializable;

class IntrospectUtils {
    public static <T> boolean hasNullProps(T obj) {
        if (obj == null) {
            return true;
        }

        try {
            return Arrays.stream(Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors())
                    .map(desc -> {
                        try {
                            return desc.getReadMethod().invoke(obj);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .anyMatch(Objects::isNull);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return true;
    }
}

class Data implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer num;
    private String str;

    public Data() {}

    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }

    public String getStr() { return str; }
    public void setStr(String str) { this.str = str; }
}

public class HasNullProps {
    public static void main(String[] args) {
        Data data = new Data();
        data.setNum(null);
        data.setStr(null);
        System.out.println(IntrospectUtils.hasNullProps(data));
    }
}
