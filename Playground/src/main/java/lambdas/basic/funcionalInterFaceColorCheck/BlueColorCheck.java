package lambdas.basic.funcionalInterFaceColorCheck;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class BlueColorCheck implements ColorCheck{
    @Override
    public boolean check(String color) {
        return color.equals("blue");
    }
}
