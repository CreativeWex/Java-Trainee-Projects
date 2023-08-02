package streamApi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ProductStreamApi {
    List<Product> products = Arrays.asList(
            new Product("Laptop", 1200.0, "Electronics"),
            new Product("Shirt", 29.99, "Apparel"),
            new Product("Chair", 199.0, "Furniture"),
            new Product("Phone", 599.0, "Electronics"),
            new Product("Jeans", 49.99, "Apparel"),
            new Product("Table", 299.0, "Furniture")
    );

    public void displayElectronics() {
        products.stream().filter(product -> product.getCategory().equals("Electronics")).forEach(System.out::println);
    }

    public void displayProductsName() {
        List<String> productNames = products.stream().map(Product::getName).toList();
        productNames.forEach(System.out::println);
    }

    public void displayTotalPriceAmount() {
        System.out.println(products.stream().mapToDouble(Product::getPrice).sum());
    }

    public void groupByCategories() {
        List<String> categories = products.stream().map(Product::getCategory).toList();
    }

    public void displayTheMostValuableProduct() {
        System.out.println(products.stream().max(Comparator.comparingDouble(Product::getPrice)).get());
    }

    public void displaySortedByPrice() {

    }

    public static void main(String[] args) {
        ProductStreamApi productStreamApi = new ProductStreamApi();
        System.out.println("ex 1");
        productStreamApi.displayElectronics();
        System.out.println("ex 2");
        productStreamApi.displayProductsName();
        System.out.println("ex 3");
        productStreamApi.displayTotalPriceAmount();
        System.out.println("ex 4");
        System.out.println("ex 5");
        productStreamApi.displayTheMostValuableProduct();
        System.out.println("ex 6");
        System.out.println("ex 7");
        System.out.println("ex 8");
        System.out.println("ex 9");
        System.out.println("ex 10");
    }
}
