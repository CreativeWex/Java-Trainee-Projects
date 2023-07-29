package lambdas;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class LambdaForInterface {
    @FunctionalInterface
    public interface Monitoring<T> {
        public void print(Object object);
    }

    public static void main(String[] args) {
        Monitoring<Object> monitoring = object -> System.out.println(object);

        monitoring.print(123);
        monitoring.print("123");
        monitoring.print(new int[] {1, 2, 3});
    }
}
