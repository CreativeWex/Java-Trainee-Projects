public class Program {
    public static void main(String[] args) {
        User recipient = new User(0, "John", 500);
        User sender = new User(1, "Mike", 500);
        System.out.println(recipient);
        System.out.println(sender);

        Transaction transaction = new Transaction(recipient, sender, -200);
        transaction.makeTransaction();
    }
}
