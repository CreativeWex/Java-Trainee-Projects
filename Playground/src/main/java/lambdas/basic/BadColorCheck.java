package lambdas.basic;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class BadColorCheck {
    private int getBlueColorNumber(String[] colors) {
        int result = 0;
        for (String color : colors) {
            if (color.equals("blue")) {
                result++;
            }
        }
        return  result;
    }

    private int getRedColorNumber(String[] colors) {
        int result = 0;
        for (String color : colors) {
            if (color.equals("red")) {
                result++;
            }
        }
        return  result;
    }

    public static void main(String[] args) {
        String[] colors = {"blue", "blue", "red", "blue"};
        BadColorCheck badColorCheck = new BadColorCheck();
        System.out.println("blue = " + badColorCheck.getBlueColorNumber(colors));
        System.out.println("red = " + badColorCheck.getRedColorNumber(colors));
    }
}
