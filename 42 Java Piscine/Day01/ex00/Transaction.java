import java.util.UUID;

public class Transaction {
    private final UUID uuid;
    private User recipient;
    private User sender;
    private Integer amount;
    private final String type;

    public Transaction(User recipient, User sender, Integer amount) {
        uuid = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.amount = amount;
        if (amount < 0) {
            type = "CREDIT";
        } else {
            type = "DEBIT";
        }
    }

    public void makeTransaction() {
        if (sender.getBalance() < Math.abs(amount) || amount == 0) {
            System.err.println("Sender's balance " + sender.getBalance() +
                    "\nInvalid transaction amount: " + amount);
            System.exit(-1);
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        System.out.println("\n===[TRANSACTION INFO]===" +
                "\nID: " + uuid +
                "\nTRANSACTION TYPE: " + type +
                "\nSENDER: " + sender.getName() +
                "\nRECIPIENT: " + recipient.getName() +
                "\nAMOUNT: " + amount);
        System.out.println("\n===[SENDER INFO]===" +
                "\nID: " + sender.getId() +
                "\nNAME: " + sender.getName() +
                "\nBALANCE: " + sender.getBalance());
        System.out.println("\n===[RECIPIENT INFO]===" +
                "\nID: " + recipient.getId() +
                "\nNAME: " + recipient.getName() +
                "\nBALANCE: " + recipient.getBalance());
        System.out.println("=================");
    }

    public Integer getAmount() {
        return amount;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}
