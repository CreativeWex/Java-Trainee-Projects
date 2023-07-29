package lambdas.basic.funcionalInterFaceColorCheck;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class FunctionalInterfaceColorCheck {
    private int getColorNumber(String[] colors, ColorCheck checker) {
        int result = 0;
        for (String color : colors) {
            if (checker.check(color)) {
                result++;
            }
        }
        return  result;
    }

    public static void main(String[] args) {
        String[] colors = {"blue", "blue", "red", "blue"};
        FunctionalInterfaceColorCheck checker = new FunctionalInterfaceColorCheck();

        // Создание интерфейса и реализация полиморфизма через разные имплементирующие классы
        System.out.println("blue = " + checker.getColorNumber(colors, new BlueColorCheck()));
        System.out.println("red = " + checker.getColorNumber(colors, new RedColorCheck()));


        // Использование анонимных методов
        String[] moreColors = {"blue", "blue", "red", "blue", "yellow", "yellow", "green", "green", "green", "green"};
        System.out.println("yellow = " + checker.getColorNumber(moreColors, new ColorCheck() {
            @Override
            public boolean check(String color) {
                return color.equals("yellow");
            }
        }));

        System.out.println("green = " + checker.getColorNumber(moreColors, new ColorCheck() {
            @Override
            public boolean check(String color) {
                return color.equals("green");
            }
        }));

        //применение лямбда-выражения
        System.out.println("green = " + checker.getColorNumber(moreColors, color -> color.equals("green")));
        System.out.println("yellow = " + checker.getColorNumber(moreColors, color -> color.equals("yellow")));
        System.out.println("size less than 4 = " + checker.getColorNumber(moreColors, color -> color.length() < 4));

        //применение лямбда-блока
        System.out.println("got 2+ 'e' letters = " + checker.getColorNumber(moreColors, color -> {
            int counter = 0;
            for (int i = 0; i < color.length(); i++) {
                if (color.charAt(i) == 'e') {
                    counter++;
                }
            }
            return counter >= 2;
        }));
    }
}
