import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User recipient = new User("John", 800);
        User sender = new User("Mike", 1200);
        Transaction fromMikeToJohn = new Transaction(recipient, sender, 100);
        fromMikeToJohn.makeTransaction();
        Transaction fromJohnToMike = new Transaction(sender, recipient, -500);
        fromJohnToMike.makeTransaction();
        Transaction lowestTransactionEver = new Transaction(sender, recipient, 1);
        lowestTransactionEver.makeTransaction();

        System.out.println("Recipient's transactions:");
        recipient.getTransactionsList().display();

        System.out.println("\n\nSender's transactions:");
        sender.getTransactionsList().display();

        System.out.println("\n\nREMOVING SENDER LAST TRANSACTION");
        UUID uuid = sender.getTransactionsList().toArray()[2].getUuid();
        sender.getTransactionsList().removeById(uuid);
        System.out.println("Recipient's transactions:");
        recipient.getTransactionsList().display();
        System.out.println("\nSender's transactions:");
        sender.getTransactionsList().display();

        System.out.println("\n\nADD NEW TRANSACTION");
        Transaction transaction = new Transaction(sender, recipient, 10);
        transaction.makeTransaction();
        System.out.println("Recipient's transactions:");
        recipient.getTransactionsList().display();
        System.out.println("\nSender's transactions:");
        sender.getTransactionsList().display();

        System.out.println("\n\nREMOVE TRANSACTION THAT DOESN'T EXISTS");
        sender.getTransactionsList().removeById(uuid);
        System.out.println("Recipient's transactions:");
        recipient.getTransactionsList().display();
        System.out.println("\nSender's transactions:");
        sender.getTransactionsList().display();
    }
}
