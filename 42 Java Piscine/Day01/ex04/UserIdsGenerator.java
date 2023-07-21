public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private int currentIdentifier = 0;

    private UserIdsGenerator() {
        currentIdentifier = 0;
    }
    public static UserIdsGenerator getInstance() {
        if (instance == null)
            instance = new UserIdsGenerator();
        return (instance);
    }
    public int generateId() {
        this.currentIdentifier++;
        return (currentIdentifier - 1);
    }
}
