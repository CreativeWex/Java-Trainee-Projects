public class UsersArrayList implements UsersList {
    private User [] array = new User[10];
    private int userAmount = 0;
    @Override
    public void addUser(User newUser) {
        for (int counter = 0; counter < userAmount; ++counter) {
            if (array[counter].getIdentifier() == newUser.getIdentifier()) {
                return;
            }
        }
        if (userAmount >= array.length) {
            User [] tempArray = new User[array.length * 2];
            for (int counter = 0; counter < array.length; ++counter) {
                tempArray[counter] = array[counter];
            }
            array = tempArray;
        }
        array[userAmount] = newUser;
        ++userAmount;
    }

    @Override
    public User findByIdentifier(int identifier) {
        for (int counter = 0; counter < userAmount; ++counter) {
            if (array[counter].getIdentifier() == identifier) {
                return array[counter];
            }
        }
        throw (new UserNotFoundException());
    }

    @Override
    public User findByIndex(int identifier) {
        if (identifier > userAmount) {
            throw (new UserNotFoundException());
        }
        return array[identifier];
    }

    @Override
    public int getUserAmount() {
        return userAmount;
    }
}
