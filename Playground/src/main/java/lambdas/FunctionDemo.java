package lambdas;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.function.DoubleToLongFunction;
import java.util.function.Function;

public class FunctionDemo {
    public static void main(String[] args) {
        Function<Double, Long> doubleToLongConverter = d -> Math.round(d);
        System.out.println(doubleToLongConverter.apply(5.23));

        Function<Long, String> longStringFunction = l -> {
            if (l < 0) {
                return "Отрицательное число";
            } else if (l > 0) {
                return "Положительное число";
            }
            return "Ноль";
        };
        System.out.println(longStringFunction.apply(-1L));
        System.out.println(longStringFunction.apply(1L));
        System.out.println(longStringFunction.apply(0L));
    }
}
