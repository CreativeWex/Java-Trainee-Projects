package lambdas;/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.function.Supplier;

public class SupplierDemo {
    public static void main(String[] args) {
        String str = "hello world";
        Supplier<String> supplier = () -> str.toUpperCase();
        System.out.println(supplier.get());
    }
}
