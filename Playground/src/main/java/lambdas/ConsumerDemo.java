package lambdas;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.function.Consumer;

public class ConsumerDemo {
    public class Store {
        public int productLeft;

        public Store(int productLeft) {
            this.productLeft = productLeft;
        }
    }


    public static void main(String[] args) {
        System.out.println("hw");
        Consumer<String> printUpperCase = s -> System.out.println(s.toUpperCase());
        Consumer<String> printLowerCase = s -> System.out.println(s.toLowerCase());
        printUpperCase.accept("Hello World");
        printLowerCase.accept("Hello World");


        //Продажа товара
        System.out.println("\nproduct test");
        Consumer<Store> sellOneProduct = store -> {
            System.out.println("product sold");
            store.productLeft--;
            System.out.println("product left = " + store.productLeft);
        };
        Store store = new ConsumerDemo().new Store(10);
        sellOneProduct.accept(store);
        sellOneProduct.accept(store);
        sellOneProduct.accept(store);

        //andThen()
        System.out.println("\nandThen()");
        printUpperCase.andThen(printLowerCase).accept("Hello World");
    }
}
