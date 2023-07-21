public interface UsersList {
    public void addUser(User newUser);
    public User findByIdentifier(int identifier) throws UserNotFoundException;
    public User findByIndex(int identifier) throws UserNotFoundException;
    public int getUserAmount();

}
