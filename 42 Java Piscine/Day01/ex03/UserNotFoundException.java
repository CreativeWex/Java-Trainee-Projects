public class UserNotFoundException extends RuntimeException{
    @Override
    public String toString() {
        return "No such user";
    }
}
