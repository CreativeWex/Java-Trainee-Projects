public class UserIdsGenerator {
    private static UserIdsGenerator instance = null;
    private static int uuid = -1;
    private UserIdsGenerator() {

    }
    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int getUuid() {
        return ++uuid;
    }
}
