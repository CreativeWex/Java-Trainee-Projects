public class UsersList implements UsersArrayLists {
    private User[] storage = new User[10];
    private int amount = -1;

    @Override
    public void add(User user) {
        if (amount >= storage.length - 1) {
            User[] newStorage = new User[storage.length * 2];
            for (int i = 0; i < storage.length; i++) {
                newStorage[i] = storage[i];
            }
            storage = newStorage;
        }
        amount++;
        storage[amount] = user;
    }

    @Override
    public User findById(int id) {
        for (User user : storage) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User findByIndex(int index) {
        if (index > amount) {
            throw (new UserNotFoundException());
        }
        return storage[index];
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void display() {
        System.out.println("\n====[Users list]====");
        for (int i = 0; i < storage.length; i++) {
            System.out.println("---[" + i + "]---");
            System.out.println(storage[i]);
        }
        System.out.println("\n=============");
    }
}
